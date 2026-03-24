# Student Register Portal 🎓

A professional, production-grade Android application for student academic management. Built with **Modern Android Development (MAD)** practices and **MVVM Architecture**.

## 🌟 Features

- **Dashboard Overview**: Real-time attendance progress and quick action shortcuts.
- **Attendance Tracking**: 
  - Subject-wise percentage tracking.
  - Smart logic: Tells you how many more classes needed for 75% or how many you can safely miss.
  - Manual daily attendance logging with a clean dialog UI.
- **MST Marks Management**:
  - Log MST-1 and MST-2 marks for each subject.
  - Visual progress bars and performance status (e.g., "Weak Subject" highlights).
  - Handles "Not happened yet" states gracefully.
- **Leave Management**:
  - Digital leave application wizard with date pickers.
  - Filter applications by status: Pending, Approved, Rejected.
- **Academic Infrastructure**:
  - Dynamic Timetable view.
  - Official Announcements feed.
- **Session Management**: Persistent login states and secure preference handling.
- **UI/UX**: Material Design 3, Dark Mode support, and smooth scrolling with hide-on-scroll navigation.

## 🛠 Tech Stack

- **Language**: Java
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Persistence (SQLite)
- **Navigation**: Jetpack Navigation Component
- **UI**: Material Components, CoordinatorLayout, ConstraintLayout, NestedScrollView
- **Components**: ViewModel, LiveData, Lifecycle, SwipeRefreshLayout

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or later.
- JDK 21.
- Android SDK 35.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/adityatiwari12/JavaApp.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle and build.
4. Run on an emulator or physical device.

## 📁 Project Structure
```text
com.example.registrationapp
├── data
│   ├── db         # Room Database, DAOs
│   ├── entity     # Data models
│   └── repository # Repository pattern for data abstraction
├── ui
│   ├── adapter    # RecyclerView Adapters
│   ├── attendance # Attendance Fragment & ViewModel
│   ├── home       # Dashboard Fragment & ViewModel
│   ├── marks      # MST Marks Fragment & ViewModel
│   └── profile    # Profile & Session Management
└── utils          # Date, Preference, and UI Utils
```

---
Built by **Aditya Tiwari**
