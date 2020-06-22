package com.mp.util;

import java.io.FileInputStream;
import java.io.InputStream;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * 文字コードを判定するクラス.
 */
public class FileCharDetecter {
	  /**
	   * 文字コードを判定するメソッド.
	   * @param ファイルパス
	   * @return 文字コード
	   */
	  public static String detector(InputStream fis) throws java.io.IOException {
	    byte[] buf = new byte[4096];

	    // 文字コード判定ライブラリの実装
	    UniversalDetector detector = new UniversalDetector(null);

	    // 判定開始
	    int nread;
	    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
	      detector.handleData(buf, 0, nread);
	    }
	    // 判定終了
	    detector.dataEnd();

	    // 文字コード判定
	    String encTyle = detector.getDetectedCharset();
	    if (encTyle != null) {
	      System.out.println("文字コード = " + encTyle);
	    } else {
	      System.out.println("文字コードを判定できませんでした");
	    }

	    // 判定の初期化
	    detector.reset();

	    return encTyle;
	  }
}
