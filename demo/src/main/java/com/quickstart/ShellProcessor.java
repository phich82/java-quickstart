package com.quickstart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellProcessor {
    private static final String WINDOWS_OS_PREFIX = "windows";
    private static final String OS_NAME_KEY = "os.name";

    public static void exec() {
        ProcessBuilder processBuilder = new ProcessBuilder();
		if (isWindowOS()) {
			// Run this on Windows, cmd, /c = terminate after this run
			processBuilder.command("cmd.exe", "/c", "ping -n 3 google.com");
		} else {
			// Sorry!!! I don't have Linux machine so not yet tested :)
			processBuilder.command("bash", "-c", "ping 3 google.com");
        }

        try {

			Process process = processBuilder.start();

			// blocked
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

            // Causes the current thread to wait, if necessary, until the
			// process represented by this Process object has terminated. This
			// method returns immediately if the subprocess has already
			// terminated. If the subprocess has not yet terminated, the calling
			// thread will be blocked until the subprocess exits.
			//
			// Returns: the exit value of the subprocess represented by this
			// Process object. By convention, the value 0 indicates normal
			// termination.
			int exitCode = process.waitFor();
			System.out.println("\nExited with error code : " + exitCode);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    private static boolean isWindowOS() {
		return System.getProperty(OS_NAME_KEY).toLowerCase().startsWith(WINDOWS_OS_PREFIX);
    }
}
