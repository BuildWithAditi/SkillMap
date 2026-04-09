# 🚀 SkillMap AI — Personalized Learning Path Generator

AI-powered full-stack web application that generates personalized learning roadmaps based on your current skills and career goals, with gamified progress tracking and quizzes.

---

## 🌟 Features

* 🔐 JWT Authentication (Login / Signup)
* 🧠 AI-generated weekly learning roadmap
* 📅 Interactive timeline-based UI
* 📝 Weekly quizzes with auto-grading
* 🎯 XP & streak gamification system
* 📊 Progress tracking
* 📧 Email reminders (JavaMail)
* 💾 MySQL database integration

---

## 🛠️ Tech Stack

### 🔹 Frontend

* React (Vite)
* Axios
* Tailwind / Custom CSS

### 🔹 Backend

* Spring Boot
* Spring Security (JWT)
* Spring Data JPA
* Hibernate

### 🔹 Database

* MySQL

### 🔹 Other

* OpenAI API (for roadmap generation)
* JavaMail (email notifications)

---

## 📸 Screenshots

### 🔐 Login Page

<img width="2940" height="1782" alt="image" src="https://github.com/user-attachments/assets/0233fb29-e572-4b25-8e5e-2903263c70a5" />


### 📊 Dashboard

<img width="1280" height="813" alt="image" src="https://github.com/user-attachments/assets/09ff90dd-9221-4a22-ab28-4b9a709d86df" />


### 🧠 Roadmap

<img width="1280" height="813" alt="image" src="https://github.com/user-attachments/assets/e4f91a7f-9d68-4a45-93a3-346efcf63e3d" />
<img width="1280" height="813" alt="image" src="https://github.com/user-attachments/assets/070f485c-e84c-467d-96d3-655ab1802d11" />



### 📝 Quiz

<img width="1280" height="813" alt="image" src="https://github.com/user-attachments/assets/43ab4536-2d38-4cc5-8f15-b90771fa43cc" />


### 🎯 Results

<img width="1280" height="812" alt="image" src="https://github.com/user-attachments/assets/423c98e7-3aac-42b9-9d63-fa33c9c6a561" />

---

## ⚙️ Setup Instructions

### 🔹 1. Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/SkillMap-AI.git
cd SkillMap-AI
```

---

### 🔹 2. Backend Setup

```bash
cd backend
./mvnw spring-boot:run
```

#### Configure `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/skillmap
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

---

### 🔹 3. Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

---

### 🔹 4. Access App

Frontend:

```
http://localhost:5173
```

Backend:

```
http://localhost:8080
```

---

## 🔐 API Endpoints

| Method | Endpoint           | Description      |
| ------ | ------------------ | ---------------- |
| POST   | /auth/register     | Register user    |
| POST   | /auth/login        | Login user       |
| POST   | /roadmap/generate  | Generate roadmap |
| GET    | /quiz/{weekNo}     | Get quiz         |
| POST   | /quiz/submit       | Submit quiz      |
| POST   | /progress/complete | Update progress  |

---

## 🎯 Project Workflow

```
Register → Login → Enter Skills → Generate Roadmap → 
Complete Weekly Tasks → Take Quiz → Gain XP & Streak
```

---

## 🚀 Future Enhancements

* 🤖 AI mentor chatbot
* 📈 Analytics dashboard
* 🧑‍🏫 Mentor matching system
* 📱 Mobile responsive improvements

---

## 👩‍💻 Author

**Aditi Parmar**
Computer Science Engineer (AI/ML)

---

---
