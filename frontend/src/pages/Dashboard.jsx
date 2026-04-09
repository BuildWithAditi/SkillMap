import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import api from '../api';

const Dashboard = () => {
  const [goal, setGoal] = useState('');
  const [skills, setSkills] = useState('');
  const [roadmap, setRoadmap] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [stats, setStats] = useState({ xp: 0, streak: 0 });
  const navigate = useNavigate();
  
  const userId = localStorage.getItem('userId');

  const generateRoadmap = async () => {
    if (!goal) return;
    setLoading(true);
    try {
      const skillsArray = skills.split(',').map(s => s.trim()).filter(Boolean);
      const res = await api.post('/roadmap/generate', {
        userId: parseInt(userId),
        skills: skillsArray,
        goal
      });
      // Backend returns Roadmap entity. 'content' holds the JSON string array.
      setRoadmap(JSON.parse(res.data.content));
    } catch (err) {
      console.error(err);
      setError('Failed to generate roadmap. Please check your connection and try again.');
    } finally {
      setLoading(false);
    }
  };

  const completeWeek = async (weekNo) => {
    try {
      const res = await api.post('/progress/complete', { userId: parseInt(userId), weekNo });
      setStats({ xp: res.data.xp, streak: res.data.streak });
      alert('Week ' + weekNo + ' Completed! XP and Streak updated!');
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="container">
      <Navbar xp={stats.xp} streak={stats.streak} />
      
      {!roadmap ? (
        <div className="glass-panel animate-fade-in" style={{ padding: '40px', textAlign: 'center', maxWidth: '600px', margin: '0 auto' }}>
          <h2 style={{ marginBottom: '16px' }}>What do you want to learn?</h2>
          <p style={{ color: 'var(--text-secondary)', marginBottom: '24px' }}>
            Enter your destination and we will plot the perfect 6-week journey for you.
          </p>
          {error && <p style={{ color: '#ef4444', marginBottom: '16px' }}>{error}</p>}
          <input
            type="text"
            className="input-field"
            placeholder="e.g. HTML, CSS, JavaScript (comma separated)"
            value={skills}
            onChange={(e) => setSkills(e.target.value)}
          />
          <input
            type="text"
            className="input-field"
            placeholder="Goal: e.g. Full-Stack Web Development"
            value={goal}
            onChange={(e) => setGoal(e.target.value)}
          />
          <button className="btn-primary" onClick={generateRoadmap} disabled={loading}>
            {loading ? 'Generating...' : 'Map My Journey'}
          </button>
        </div>
      ) : (
        <div className="roadmap-view animate-fade-in">
          <h2 style={{ marginBottom: '32px', textAlign: 'center' }}>Your Learning Journey</h2>
          
          <div className="timeline">
            {roadmap.map((weekData) => (
              <div key={weekData.week} className="glass-panel timeline-card">
                <div className="timeline-dot"></div>
                <h3 className="week-title">Week {weekData.week}: {weekData.title}</h3>
                
                <ul className="task-list">
                  {weekData.tasks && weekData.tasks.map((task, idx) => (
                    <li key={idx} className="task-item">{task}</li>
                  ))}
                </ul>

                <div style={{ display: 'flex', gap: '12px', marginTop: '20px' }}>
                  <button 
                    className="btn-success" 
                    onClick={() => completeWeek(weekData.week)}
                  >
                    Mark Complete
                  </button>
                  <button 
                    className="btn-secondary" 
                    onClick={() => navigate(`/quiz/${weekData.week}`)}
                  >
                    Take Quiz
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
