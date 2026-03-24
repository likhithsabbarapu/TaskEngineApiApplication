import { useState, useEffect } from 'react';
import { api } from './api/axios';
import { Trash2, Plus, LayoutDashboard, Circle, AlertCircle, CheckCircle } from 'lucide-react';
import './App.css';

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [status, setStatus] = useState("🔄 Connecting...");
  const [isConnected, setIsConnected] = useState(false);
  const [newTitle, setNewTitle] = useState("");

  useEffect(() => {
    fetchTasks();
    // Refresh tasks every 5 seconds
    const interval = setInterval(fetchTasks, 5000);
    return () => clearInterval(interval);
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await api.get('/tasks');
      setTasks(response.data.content || response.data || []);
      setStatus("✅ Connected");
      setIsConnected(true);
    } catch (err) {
      console.error("Connection error:", err.message);
      setStatus("❌ Backend Offline - Is Docker running?");
      setIsConnected(false);
      setTasks([]);
    }
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    if (!title.trim()) return;
    
    try {
      const res = await api.post('/tasks', { 
        title: title.trim(), 
        status: 'TODO',
        description: ''
      });
      setTasks([...tasks, res.data]);
      setTitle("");
      setStatus("✅ Task added successfully");
    } catch (err) {
      console.error("Add task error:", err);
      setStatus("❌ Failed to add task");
      alert("Error adding task. Check backend connection.");
    }
  };

  const handleDelete = async (id) => {
    try {
      await api.delete(`/tasks/${id}`);
      setTasks(tasks.filter(t => t.id !== id));
      setStatus("✅ Task deleted");
    } catch (err) {
      console.error("Delete error:", err);
      setStatus("❌ Failed to delete task");
    }
  };

  const handleStatusChange = async (id, newStatus) => {
    try {
      const task = tasks.find(t => t.id === id);
      await api.put(`/tasks/${id}`, {
        ...task,
        status: newStatus
      });
      setTasks(tasks.map(t => t.id === id ? {...t, status: newStatus} : t));
      setStatus("✅ Task updated");
    } catch (err) {
      console.error("Update error:", err);
      setStatus("❌ Failed to update task");
    }
  };

  const getStatusColor = (status) => {
    switch(status) {
      case 'TODO': return '#ef4444';
      case 'IN_PROGRESS': return '#f59e0b';
      case 'DONE': return '#10b981';
      default: return '#6b7280';
    }
  };

  return (
    <div style={{ minHeight: '100vh', backgroundColor: '#f9fafb', padding: '40px 20px', fontFamily: 'sans-serif' }}>
      <div style={{ maxWidth: '600px', margin: '0 auto', backgroundColor: 'white', padding: '30px', borderRadius: '20px', boxShadow: '0 10px 25px rgba(0,0,0,0.1)' }}>
        
        {/* Header */}
        <header style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '30px' }}>
          <LayoutDashboard style={{ color: '#2563eb' }} size={32} />
          <div>
            <h1 style={{ fontSize: '28px', fontWeight: 'bold', color: '#1f2937', margin: '0 0 5px 0' }}>
              TaskEngine <span style={{ color: '#2563eb' }}>Pro</span>
            </h1>
            <p style={{ fontSize: '12px', color: '#9ca3af', margin: 0 }}>Powered by React + Spring Boot</p>
          </div>
        </header>

        {/* Status Banner */}
        <div style={{
          padding: '12px 15px',
          borderRadius: '12px',
          marginBottom: '20px',
          display: 'flex',
          alignItems: 'center',
          gap: '10px',
          backgroundColor: isConnected ? '#d1fae5' : '#fee2e2',
          color: isConnected ? '#065f46' : '#7f1d1d',
          fontSize: '14px'
        }}>
          {isConnected ? 
            <CheckCircle size={18} /> : 
            <AlertCircle size={18} />
          }
          <span>{status}</span>
        </div>

        {/* Add Task Form */}
        <form onSubmit={handleAdd} style={{ display: 'flex', gap: '10px', marginBottom: '25px' }}>
          <input 
            style={{
              flex: 1,
              padding: '12px',
              borderRadius: '12px',
              border: '1px solid #e5e7eb',
              outline: 'none',
              fontSize: '14px',
              transition: 'border-color 0.2s'
            }}
            placeholder="Add a new task..."
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            disabled={!isConnected}
            onFocus={(e) => e.target.style.borderColor = '#2563eb'}
            onBlur={(e) => e.target.style.borderColor = '#e5e7eb'}
          />
          <button
            type="submit"
            disabled={!isConnected}
            style={{
              backgroundColor: isConnected ? '#2563eb' : '#9ca3af',
              color: 'white',
              border: 'none',
              padding: '12px 16px',
              borderRadius: '12px',
              cursor: isConnected ? 'pointer' : 'not-allowed',
              display: 'flex',
              alignItems: 'center',
              gap: '6px'
            }}
          >
            <Plus size={20} />
            Add
          </button>
        </form>

        {/* Tasks List */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '12px', marginBottom: '20px' }}>
          {tasks.length === 0 ? (
            <div style={{
              padding: '30px',
              textAlign: 'center',
              color: '#9ca3af'
            }}>
              <p>No tasks yet. Add one to get started! 🚀</p>
            </div>
          ) : (
            tasks.map(task => (
              <div key={task.id} style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                padding: '15px',
                backgroundColor: '#f3f4f6',
                borderRadius: '12px',
                borderLeft: `4px solid ${getStatusColor(task.status)}`
              }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '12px', flex: 1 }}>
                  <Circle size={18} color={getStatusColor(task.status)} fill={getStatusColor(task.status)} />
                  <div style={{ flex: 1 }}>
                    <p style={{ color: '#374151', fontWeight: '500', margin: '0 0 5px 0' }}>
                      {task.title}
                    </p>
                    <p style={{ color: '#9ca3af', fontSize: '12px', margin: 0 }}>
                      Status: <span style={{ color: getStatusColor(task.status), fontWeight: 'bold' }}>
                        {task.status}
                      </span>
                    </p>
                  </div>
                </div>
                <div style={{ display: 'flex', gap: '8px' }}>
                  <select
                    value={task.status}
                    onChange={(e) => handleStatusChange(task.id, e.target.value)}
                    disabled={!isConnected}
                    style={{
                      padding: '6px 10px',
                      borderRadius: '8px',
                      border: `1px solid ${getStatusColor(task.status)}`,
                      color: getStatusColor(task.status),
                      backgroundColor: 'white',
                      cursor: isConnected ? 'pointer' : 'not-allowed',
                      fontSize: '12px'
                    }}
                  >
                    <option value="TODO">TODO</option>
                    <option value="IN_PROGRESS">IN PROGRESS</option>
                    <option value="DONE">DONE</option>
                  </select>
                  <button
                    onClick={() => handleDelete(task.id)}
                    disabled={!isConnected}
                    style={{
                      background: 'none',
                      border: 'none',
                      color: isConnected ? '#ef4444' : '#9ca3af',
                      cursor: isConnected ? 'pointer' : 'not-allowed',
                      padding: '6px',
                      transition: 'color 0.2s'
                    }}
                    onMouseEnter={(e) => e.target.style.color = '#dc2626'}
                    onMouseLeave={(e) => e.target.style.color = isConnected ? '#ef4444' : '#9ca3af'}
                  >
                    <Trash2 size={18} />
                  </button>
                </div>
              </div>
            ))
          )}
        </div>

        {/* Footer Info */}
        <div style={{
          borderTop: '1px solid #e5e7eb',
          paddingTop: '20px',
          fontSize: '12px',
          color: '#9ca3af'
        }}>
          <p style={{ margin: '0 0 5px 0' }}>
            📊 Total Tasks: <strong>{tasks.length}</strong>
          </p>
          <p style={{ margin: '0 0 5px 0' }}>
            ✅ Completed: <strong>{tasks.filter(t => t.status === 'DONE').length}</strong>
          </p>
          <p style={{ margin: '0 0 5px 0' }}>
            🔄 In Progress: <strong>{tasks.filter(t => t.status === 'IN_PROGRESS').length}</strong>
          </p>
          <p style={{ margin: '0 0 10px 0' }}>
            📝 To Do: <strong>{tasks.filter(t => t.status === 'TODO').length}</strong>
          </p>
          <p style={{ margin: 0, color: '#d1d5db' }}>
            API: {isConnected ? '🟢' : '🔴'} {isConnected ? 'http://localhost:8080/api' : 'Offline'}
          </p>
        </div>
      </div>
    </div>
  );
}

export default App;

