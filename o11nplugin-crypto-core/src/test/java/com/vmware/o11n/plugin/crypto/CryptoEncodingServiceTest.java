/*
 * Copyright (c) 2017 VMware, Inc. All Rights Reserved.
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.vmware.o11n.plugin.crypto;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import com.vmware.o11n.plugin.crypto.service.CryptoEncodingService;
import com.vmware.o11n.plugin.crypto.service.CryptoEncryptionService;

public class CryptoEncodingServiceTest {

	CryptoEncodingService service = new CryptoEncodingService();
	CryptoEncryptionService encryptionService = new CryptoEncryptionService();
	private final String staticString = "Hello World!!";
	private final String staticStringB64 = "SGVsbG8gV29ybGQhIQ==";
	private final String staticB32foobar = "MZXW6YTBOI======";
	private final String staticB32fooba = "MZXW6YTB";

	@Test
	public void staticB64EncodeTest() throws UnsupportedEncodingException {
		assertEquals("staticB64Encode", staticStringB64, service.base64Encode(staticString));
	}
	@Test
	public void staticB64DecodeTest() {
		assertEquals("staticB64Decode", staticString, service.base64Decode(staticStringB64));
	}
	@Test
	public void b64toHexAndBack() throws DecoderException {
		String hex = service.base64toHex(staticStringB64);
		assertEquals("b64toHexAndBack", staticStringB64, service.hexToBase64(hex));
	}
	@Test
	public void getLengthBase64Test() {
		final int aByte = 1;  //just one
		final int oddLength = 43;  //random odd number
		final int evenLength = 84;  //random even number

		String oddB64 = encryptionService.generateRandomBytes(oddLength);
		String evenB64 = encryptionService.generateRandomBytes(evenLength);
		String aByteB64 = encryptionService.generateRandomBytes(aByte);

		assertEquals("odd length", oddLength, service.getLengthBase64(oddB64));
		assertEquals("even length", evenLength, service.getLengthBase64(evenB64));
		assertEquals("one byte", aByte, service.getLengthBase64(aByteB64));
		assertEquals("zero bytes", 0, service.getLengthBase64(""));
	}

	@Test
	public void staticB32EncodeTest() {
		assertEquals("staticBase32foobaEncode", staticB32fooba, service.base32Encode("fooba"));
		assertEquals("staticBase32foobarEncode", staticB32foobar, service.base32Encode("foobar"));
	}

	@Test
	public void staticB32DecodeTest() {
		assertEquals("staticBase32foobaDecode", "fooba", service.base32Decode(staticB32fooba));
		assertEquals("staticBase32foobarDecode", "foobar", service.base32Decode(staticB32foobar));
	}
}