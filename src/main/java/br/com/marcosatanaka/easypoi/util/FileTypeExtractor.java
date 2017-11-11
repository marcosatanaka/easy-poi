package br.com.marcosatanaka.easypoi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Strings.isNullOrEmpty;

public class FileTypeExtractor {

	private static final Pattern FILENAME_PATTERN = Pattern.compile("^.*\\.(?<extensao>.*)$");

	private FileTypeExtractor() {
	}

	public static String extract(String filename) {
		if (!isNullOrEmpty(filename)) {
			Matcher matcher = FILENAME_PATTERN.matcher(filename);
			if (matcher.matches()) {
				return matcher.group("extensao");
			}
		}

		return null;
	}

}
