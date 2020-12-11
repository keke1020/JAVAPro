package com.mp.util;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CsvUtil {
	private static Pattern datePattern = Pattern.compile("^\\d{4}/\\d{1,2}/\\d{1,2}$");
	private static Pattern numPattern = Pattern.compile("^-?\\d+(\\.\\d*)?$");

	// CSVデータを1行読み込みます
		public static List<String> readCsv(Reader r) throws IOException {
			int c = r.read();
			if (c == -1) {
				return null;
			}
			List<String> ret = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			boolean q = false;
			boolean qe = false;
			boolean cr = false;
			while (c != -1) {
				if (c == '"') { // ダブルクオート["]の処理
					if (!q) {
						q = true;
					} else if (!qe) {
						qe = true;
					} else {
						// 値にダブルクオートを追加
						sb.append("\"");
						qe = false;
					}
				} else if (c == '\r') { // CRならばフラグを立てる
					cr = true;
				} else {
					if (qe) {
						q = false;
						qe = false;
					}
					if (!q && cr && c == '\n') { // CRLFならば行の区切り
						break;
					}
					cr = false;
					if (!q && c == ',') { // カンマ[,]ならば値の区切り
						ret.add(sb.toString());
						sb = new StringBuffer();
					} else if (c == '\n') { // LFならば値に改行[CRLF]を追加
						if (q) {
							sb.append("\r\n");
						}
					} else {
						// それ以外の文字を値に追加
						sb.append((char) c);
					}
				}
				c = r.read();
			}
			ret.add(sb.toString());
			return ret;
		}

		// 値を適切な型に変換して返します
		private static Object parseValue(String v) {
			try {
				if (v != null) {
					if (datePattern.matcher(v).find()) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy/M/d");
						return df.parse(v);
					} else if (numPattern.matcher(v).find()) {
						return new BigDecimal(v);
					}
				}
			} catch (Exception e) {
			}
			return v;
		}

		public static String getValue(String[] item, int index) {
			String value = "";
			if (item == null) {

			} else if (item.length > index) {
				value = item[index].replace("\"", "");
			}
			if ("null".equals(value)) {
				value = "";
			}
			return value;
		}
}
