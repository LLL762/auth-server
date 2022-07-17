package com.delacasa.auth.jwt;

import static java.time.Instant.now;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delacasa.auth.exception.JwtTokenException;
import com.delacasa.auth.security.CustomAuth;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtServiceImpl implements JwtService<SignedJWT> {

	@Autowired
	private JwtConfig jwtConfig;

	private final RSAKey senderJWK;
	private final RSAKey senderPublicJWK;

	private final RSAKey recipientJWK;
	private final RSAKey recipientPublicJWK;

	public JwtServiceImpl() throws JOSEException {

		senderJWK = generateSenderJwk();
		senderPublicJWK = senderJWK.toPublicJWK();

		recipientJWK = generateRecipientJwk();
		recipientPublicJWK = recipientJWK.toPublicJWK();

	}

	@Override
	public String createToken(final CustomAuth auth) {

		try {
			return generateJWE(generateJWS(auth)).serialize();
		} catch (JOSEException e) {
			throw new JwtTokenException(":(");
		}

	}

	@Override
	public String createRefreshToken(final CustomAuth auth) {
		try {
			return generateJWE(generateRefreshJWS(auth)).serialize();
		} catch (JOSEException e) {
			throw new JwtTokenException(":(");
		}
	}

	@Override
	public SignedJWT readToken(final String token) {

		final JWEObject jweObject;
		final SignedJWT signedJWT;

		try {

			jweObject = JWEObject.parse(token);
			jweObject.decrypt(new RSADecrypter(recipientJWK));
			signedJWT = jweObject.getPayload().toSignedJWT();

			if (!signedJWT.verify(new RSASSAVerifier(senderPublicJWK))) {
				throw new JwtTokenException("Invalid signature");
			}

		} catch (JOSEException e) {
			throw new JwtTokenException(e.getMessage() + ":)");
		} catch (ParseException e) {
			throw new JwtTokenException(e.getMessage());
		}

		return signedJWT;
	}

	private JWEObject generateJWE(SignedJWT jws) throws JOSEException {

		final JWEObject output = new JWEObject(
				new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).contentType("JWT")
						.build(),
				new Payload(jws));

		output.encrypt(new RSAEncrypter(recipientPublicJWK));

		return output;

	}

	private SignedJWT generateJWS(final CustomAuth auth) throws JOSEException {

		final SignedJWT output = new SignedJWT(
				new JWSHeader.Builder(JWSAlgorithm.RS256)
						.keyID(senderJWK.getKeyID())
						.build(),

				setUpClaims(auth));

		output.sign(new RSASSASigner(senderJWK));

		return output;

	}

	private SignedJWT generateRefreshJWS(final CustomAuth auth) throws JOSEException {

		final SignedJWT output = new SignedJWT(
				new JWSHeader.Builder(JWSAlgorithm.RS256)
						.keyID(senderJWK.getKeyID())
						.build(),

				setUpRefreshClaims(auth));

		output.sign(new RSASSASigner(senderJWK));

		return output;

	}

	private JWTClaimsSet setUpClaims(final CustomAuth auth) {

		return new JWTClaimsSet.Builder()

				.subject(auth.getPrincipal())
				.issuer(jwtConfig.getIssuer())
				.audience(jwtConfig.getAudience())
				.issueTime(new Date())
				.expirationTime(Date.from(now().plusSeconds(jwtConfig.getExpirationInSeconds())))
				.claim(jwtConfig.getClaimIp(), auth.getDetails().get(jwtConfig.getClaimIp()))
				.claim(jwtConfig.getClaimAuthorities(), auth.getAuthorities())
				.build();

	}

	private JWTClaimsSet setUpRefreshClaims(final CustomAuth auth) {

		return new JWTClaimsSet.Builder()

				.subject(auth.getPrincipal())
				.issuer(jwtConfig.getIssuer())
				.audience(jwtConfig.getAudience())
				.issueTime(new Date())
				.expirationTime(Date.from(now().plusSeconds(jwtConfig.getRefreshExpirationInSeconds())))
				.claim(jwtConfig.getClaimIp(), auth.getDetails().get(jwtConfig.getClaimIp()))
				.build();

	}

	private RSAKey generateSenderJwk() throws JOSEException {

		return new RSAKeyGenerator(2048).keyID("123")
				.keyUse(KeyUse.SIGNATURE)
				.generate();

	}

	private RSAKey generateRecipientJwk() throws JOSEException {

		return new RSAKeyGenerator(2048).keyID("321")
				.keyUse(KeyUse.ENCRYPTION)
				.generate();

	}

}
