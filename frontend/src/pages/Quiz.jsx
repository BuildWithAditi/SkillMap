import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import api from '../api';

const Quiz = () => {
  const { weekNo } = useParams();
  const navigate = useNavigate();
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        setLoading(true);
        setError('');
        const res = await api.get(`/quiz/${weekNo}`);
        setQuestions(res.data);
      } catch (err) {
        console.error(err);
        setError(err.response?.data?.message || 'Failed to fetch quiz questions. Please try again.');
      } finally {
        setLoading(false);
      }
    };
    fetchQuestions();
  }, [weekNo]);

  const handleAnswerChange = (qId, value) => {
    setAnswers(prev => ({ ...prev, [qId]: value }));
  };

  const submitQuiz = async () => {
    try {
      const res = await api.post('/quiz/submit', {
        weekNo: parseInt(weekNo),
        answers
      });
      setResult(res.data);
    } catch (err) {
      console.error(err);
      setError('Failed to submit the quiz. Please try again.');
    }
  };

  if (result) {
    return (
      <div className="container">
        <Navbar />
        <div className="glass-panel animate-fade-in quiz-container" style={{ padding: '40px', textAlign: 'center' }}>
          <h2>Quiz Results: Week {weekNo}</h2>
          <div style={{ margin: '32px 0', fontSize: '3rem', fontWeight: 'bold', color: 'var(--primary)' }}>
            {result.score} / {result.totalQuestions}
          </div>
          
          <div style={{ textAlign: 'left', marginTop: '24px' }}>
            <h3 style={{ marginBottom: '16px' }}>Review:</h3>
            {questions.map(q => (
              <div key={q.id} style={{ marginBottom: '16px', padding: '16px', background: 'rgba(255,255,255,0.05)', borderRadius: '8px' }}>
                <p><strong>Q:</strong> {q.question}</p>
                <p style={{ color: 'var(--text-secondary)' }}>Your Answer: {answers[q.id] || 'Not answered'}</p>
                <p style={{ color: 'var(--accent)' }}>Correct Answer: {result.correctAnswers[q.id]}</p>
              </div>
            ))}
          </div>

          <button className="btn-primary" onClick={() => navigate('/dashboard')} style={{ marginTop: '24px' }}>
            Back to Dashboard
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="container">
      <Navbar />
      <div className="quiz-container animate-fade-in">
        <h2 style={{ marginBottom: '24px' }}>Week {weekNo} Challenge</h2>
        
        {loading ? (
          <p>Loading questions...</p>
        ) : error ? (
          <div>
            <p style={{ color: '#ef4444', marginBottom: '16px' }}>{error}</p>
            <button className="btn-secondary" onClick={() => navigate('/dashboard')}>Back to Dashboard</button>
          </div>
        ) : questions.length === 0 ? (
          <div>
            <p style={{ marginBottom: '16px' }}>No questions available for this week yet.</p>
            <button className="btn-secondary" onClick={() => navigate('/dashboard')}>Back to Dashboard</button>
          </div>
        ) : (
          <div>
            {questions.map((q, idx) => (
              <div key={q.id} className="glass-panel question-card">
                <p className="question-text">{idx + 1}. {q.question}</p>
                <input
                  type="text"
                  className="input-field"
                  placeholder="Type your answer here..."
                  value={answers[q.id] || ''}
                  onChange={(e) => handleAnswerChange(q.id, e.target.value)}
                />
              </div>
            ))}
            <div style={{ display: 'flex', gap: '16px', marginTop: '32px' }}>
              <button className="btn-primary" onClick={submitQuiz}>Submit Answers</button>
              <button className="btn-secondary" onClick={() => navigate('/dashboard')}>Cancel</button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Quiz;
