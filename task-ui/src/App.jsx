import { useState, useEffect } from 'react';
import { api } from './api/axios'; // Adding the curly braces { } // Verified path from your screenshot
import { Trash2, Plus, LayoutDashboard, Circle } from 'lucide-react'; // Now that it's installed!

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [status, setStatus] = useState("Connecting...");

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await api.get('/tasks');
      setTasks(response.data);
      setStatus("System Online");
    } catch (err) {
      setStatus("Backend Offline (Port 8080)");
    }
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    if (!title) return;
    try {
      const res = await api.post('/tasks', { title, status: 'TODO' });
      setTasks([...tasks, res.data]);
      setTitle("");
    } catch (err) {
      alert("Database error!");
    }
  };

  const handleDelete = async (id) => {
    try {
      await api.delete(`/tasks/${id}`);
      setTasks(tasks.filter(t => t.id !== id));
    } catch (err) {
      console.error("Delete failed");
    }
  };

  return (
    <div style={{ minHeight: '100vh', backgroundColor: '#f9fafb', padding: '40px', fontFamily: 'sans-serif' }}>
      <div style={{ maxWidth: '500px', margin: '0 auto', backgroundColor: 'white', padding: '30px', borderRadius: '20px', boxShadow: '0 10px 25px rgba(0,0,0,0.05)' }}>
        
        <header style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '30px' }}>
          <LayoutDashboard style={{ color: '#2563eb' }} size={28} />
          <h1 style={{ fontSize: '24px', fontWeight: 'bold', color: '#1f2937', margin: 0 }}>TaskEngine <span style={{ color: '#2563eb' }}>Pro</span></h1>
        </header>

        <form onSubmit={handleAdd} style={{ display: 'flex', gap: '10px', marginBottom: '25px' }}>
          <input 
            style={{ flex: 1, padding: '12px', borderRadius: '12px', border: '1px solid #e5e7eb', outline: 'none' }}
            placeholder="New task title..."
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <button style={{ backgroundColor: '#2563eb', color: 'white', border: 'none', padding: '12px', borderRadius: '12px', cursor: 'pointer' }}>
            <Plus size={20} />
          </button>
        </form>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
          {tasks.map(task => (
            <div key={task.id} style={{ display: 'flex', alignItems: 'center', justifyContent: 'between', padding: '15px', backgroundColor: '#f3f4f6', borderRadius: '12px' }}>
              <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flex: 1 }}>
                <Circle size={18} color="#9ca3af" />
                <span style={{ color: '#374151', fontWeight: '500' }}>{task.title}</span>
              </div>
              <button onClick={() => handleDelete(task.id)} style={{ background: 'none', border: 'none', color: '#9ca3af', cursor: 'pointer' }}>
                <Trash2 size={18} />
              </button>
            </div>
          ))}
        </div>
        
        <p style={{ marginTop: '20px', fontSize: '12px', color: '#9ca3af', textAlign: 'center' }}>{status}</p>
      </div>
    </div>
  );
}

export default App;