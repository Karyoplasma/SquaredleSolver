package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class SquaredlePuzzleManager {

	private String dateString, regular, express;

	public SquaredlePuzzleManager() {
		this.dateString = getTodayDateString();
		this.regular = "";
		this.express = "";
	}

	private boolean isUpToDate() {
		Path savePath = Paths.get("resource/today-puzzle-config.js");
		if (savePath.toFile().exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(savePath.toFile()))) {
				String in = "";
				while ((in = reader.readLine()) != null) {
					if (!in.contains("TodayDateStr")) {
						continue;
					}
					if (in.contains(this.dateString)) {
						return true;
					} else {
						return false;
					}
				}
			} catch (FileNotFoundException e) {
				return false;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	public void readPuzzles() {
		Path savePath = Paths.get("resource/today-puzzle-config.js");
		try (BufferedReader reader = new BufferedReader(new FileReader(savePath.toFile()))) {
			String in = "";
			while ((in = reader.readLine()) != null) {
				if (!in.contains("\"board\"")) {
					continue;
				}
				if (this.express.isEmpty()) {
					while (!(in = reader.readLine()).contains("]")) {
						if (in.isEmpty()) {
							continue;
						}
						this.express += in.trim();
					}
					this.express = this.express.substring(1, this.express.length() - 1).replaceAll("\",\"", "-");
					continue;
				}
				if (this.regular.isEmpty()) {
					while (!(in = reader.readLine()).contains("]")) {
						if (in.isEmpty()) {
							continue;
						}
						this.regular += in.trim();
					}
				}
				this.regular = this.regular.substring(1, this.regular.length() - 1).replaceAll("\",\"", "-");
				return;
			}
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			return;
		}
	}

	private String getTodayDateString() {
		Instant now = Instant.now();
		Instant adjusted = now.minusSeconds(12 * 3600);
		LocalDateTime adjustedDate = LocalDateTime.ofInstant(adjusted, ZoneOffset.UTC);

		// Format the date in the desired format (YYYY/MM/DD)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return adjustedDate.format(formatter);
	}

	public void downloadPuzzleConfig() {
		if (this.isUpToDate()) {
			return;
		}
		String fileUrl = "https://squaredle.app/api/today-puzzle-config.js";
		Path savePath = Paths.get("resource/today-puzzle-config.js");

		try (InputStream inputStream = new URL(fileUrl).openStream()) {
			Files.copy(inputStream, savePath);
		} catch (IOException e) {
			System.err.println("Failed to download the file: ");
			e.printStackTrace();
		}
	}

	public String getRegular() {
		return regular;
	}

	public String getExpress() {
		return express;
	}
}
