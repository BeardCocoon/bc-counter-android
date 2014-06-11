package com.beardcocoon.counterapp.android.util;

import android.util.Log;

import com.beardcocoon.counterapp.android.BuildConfig;

import java.util.ArrayList;

public class LogWrap {
	private static ArrayList<String> mUnloggables = new ArrayList<String>();

	public static void setLoggable(boolean loggable) {
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		if (!loggable) {
			mUnloggables.add(stackTrace[1].getFileName());
		} else {
			mUnloggables.remove(stackTrace[1].getFileName());
		}
	}

	public static void d(String Tag, String... args) {
		if (BuildConfig.DEBUG) {
			StringBuilder builder = processVarArgs(args);

			Log.d(Tag, builder.toString());
		}
	}

	public static void d(String Tag, Exception e, String... args) {
		if (BuildConfig.DEBUG) {
			StringBuilder builder = processVarArgs(args);

			Log.d(Tag, builder.toString(), e);
		}
	}

	public static void l(String... args) {
		if (BuildConfig.DEBUG) {
			StringBuilder builder = processVarArgs(args);
			StackTraceElement[] stackTrace = new Throwable().getStackTrace();

			if (!mUnloggables.contains(stackTrace[1].getFileName())) {

				builder.insert(0, "[" + stackTrace[1].getMethodName() + "] ");

				Log.d(stackTrace[1].getFileName(), builder.toString());
			}
		}
	}

	private static StringBuilder processVarArgs(String... args) {
		StringBuilder builder = new StringBuilder();
		for (String string : args) {
			builder.append(string);
			builder.append("\n");
		}
		return builder;
	}
}