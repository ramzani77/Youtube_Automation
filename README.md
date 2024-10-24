# 🚀 YouTube Automation Project

## 🎯 Project Overview
This project automates various flows on the **YouTube** website using **Selenium**, **TestNG**, and **Apache POI**. It includes testing different tabs like "Films," "Music," and "News," performing soft assertions, and retrieving specific details from those tabs. Additionally, it reads search items from an Excel sheet, automating searches, and verifying the view count.

### 🛠️ Technologies Used:
- 🖥️ **Java**
- 🕸️ **Selenium**
- 🧪 **TestNG**
- 📄 **Apache POI**
- 🛠️ **Gradle**

---

## 🌟 Features
1. **🔗 URL Verification**: Asserts that the user is on the correct YouTube URL.
2. **ℹ️ About Page**: Clicks on the "About" section and prints the message on the console.
3. **🎬 Films Tab**: Verifies the movie ratings and genres in the "Top Selling" section using soft assertions.
4. **🎵 Music Tab**: Scrolls through the playlists and performs assertions on the number of tracks.
5. **📰 News Tab**: Prints the title and body of the top 3 news posts along with their like counts.
6. **🔍 Search Functionality**: Reads search items from an Excel sheet and performs automated searches until a cumulative view count of 10 Cr is reached for the items.

---

## 🏃‍♂️ How to Run

### Prerequisites:
- ☕ **Java** (JDK 8 or above)
- ⚙️ **Gradle**
- ✅ **TestNG**
- 🕸️ **Selenium WebDriver**
- 🗂️ **Apache POI**

### Setup Instructions:
1. Clone this repository:
   ```bash
   git clone https://github.com/your-github-username/ME_QA_XYOUTUBE_SEARCH.git

📁 Project Structure

ME_QA_XYOUTUBE_SEARCH/
│
├── src
│   ├── main
│   │   └── java
│   │       └── demo
│   │           ├── Wrapper.java
│   │           └── TestCases.java
│   └── test
│       └── resources
│           └── data.xlsx
│
├── build.gradle
├── testng.xml
└── README.md

