import React from 'react';
import { useNavigate } from 'react-router-dom';

const Navbar = ({ xp, streak }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    navigate('/login');
  };

  return (
    <nav className="navbar glass-panel">
      <div className="nav-brand">SkillMap</div>
      <div className="user-stats">
        
        <div className="stat-group">
          <div className="stat-badge">
            <span className="xp-text">⚡ XP:</span> {xp || 0}
          </div>
          <div className="progress-container">
            <div className="progress-fill" style={{ width: `${(xp % 100) || 5}%` }}></div>
          </div>
        </div>

        <div className="stat-badge">
          <span className="streak-text glowing-icon">🔥</span> 
          <span>Streak: {streak || 0}</span>
        </div>
        
        <button onClick={handleLogout} className="btn-secondary" style={{ padding: '8px 16px', fontSize: '0.85rem' }}>
          Logout
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
