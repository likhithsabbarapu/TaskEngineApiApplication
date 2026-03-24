import { useState, useEffect } from 'react';
import { api } from './api/axios';
import { Trash2, Plus, CheckCircle, Circle, Wifi, WifiOff } from 'lucide-react';
import './App.css';

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [isConnected, setIsConnected] = useState(false);
  const [loading, setLoading] = useState(false);
  const [filterStatus, setFilterStatus] = useState('ALL');
  const [deleteAnimation, setDeleteAnimation] = useState(null);

  useEffect(() => {
    fetchTasks();
    const interval = setInterval(fetchTasks, 5000);
    return () => clearInterval(interval);
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await api.get('/tasks');
      setTasks(response.data.content || response.data || []);
      setIsConnected(true);
    } catch (err) {
      console.error("Connection error:", err.message);
      setIsConnected(false);
      setTasks([]);
    }
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    if (!title.trim()) return;
    
    setLoading(true);
    try {
      const res = await api.post('/tasks', { 
        title: title.trim(), 
        status: 'TODO',
        description: ''
      });
      setTasks([...tasks, res.data]);
      setTitle("");
    } catch (err) {
      console.error("Add task error:", err);
      alert("Error adding task. Check backend connection.");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    setDeleteAnimation(id);
    setTimeout(async () => {
      try {
        await api.delete(`/tasks/${id}`);
        setTasks(tasks.filter(t => t.id !== id));
      } catch (err) {
        console.error("Delete error:", err);
        setDeleteAnimation(null);
      }
    }, 300);
  };

  const handleStatusChange = async (id, newStatus) => {
    const task = tasks.find(t => t.id === id);
    try {
      await api.put(`/tasks/${id}`, {
        ...task,
        status: newStatus
      });
      setTasks(tasks.map(t => t.id === id ? {...t, status: newStatus} : t));
    } catch (err) {
      console.error("Update error:", err);
    }
  };

  const getStatusInfo = (status) => {
    const statusMap = {
      'TODO': { color: '#FF3B30', label: 'To Do', bgColor: 'rgba(255, 59, 48, 0.1)' },
      'IN_PROGRESS': { color: '#FF9500', label: 'In Progress', bgColor: 'rgba(255, 149, 0, 0.1)' },
      'DONE': { color: '#34C759', label: 'Done', bgColor: 'rgba(52, 199, 89, 0.1)' }
    };
    return statusMap[status] || { color: '#8E8E93', label: 'Unknown', bgColor: 'rgba(142, 142, 147, 0.1)' };
  };

  const filteredTasks = tasks.filter(t => filterStatus === 'ALL' ? true : t.status === filterStatus);
  const stats = {
    total: tasks.length,
    done: tasks.filter(t => t.status === 'DONE').length,
    inProgress: tasks.filter(t => t.status === 'IN_PROGRESS').length,
    todo: tasks.filter(t => t.status === 'TODO').length
  };

  return (
    <div style={{
      minHeight: '100vh',
      backgroundColor: '#F2F2F7',
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif',
      paddingBottom: '40px'
    }}>
      {/* Header */}
      <header style={{
        paddingTop: '16px',
        paddingBottom: '12px',
        paddingLeft: '16px',
        paddingRight: '16px',
        backgroundColor: '#FFFFFF',
        borderBottomWidth: '1px',
        borderBottomColor: '#E5E5EA',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
      }}>
        <div>
          <h1 style={{
            fontSize: '32px',
            fontWeight: '700',
            color: '#000000',
            margin: '0 0 4px 0',
            letterSpacing: '-0.5px'
          }}>
            Tasks
          </h1>
          <p style={{
            fontSize: '13px',
            color: '#8E8E93',
            margin: 0
          }}>
            {tasks.length} {tasks.length === 1 ? 'task' : 'tasks'}
          </p>
        </div>
        <div style={{
          display: 'flex',
          alignItems: 'center',
          gap: '8px',
          padding: '6px 10px',
          backgroundColor: isConnected ? '#E8F5E9' : '#FFEBEE',
          borderRadius: '20px'
        }}>
          {isConnected ? (
            <Wifi size={14} color="#34C759" />
          ) : (
            <WifiOff size={14} color="#FF3B30" />
          )}
          <span style={{
            fontSize: '12px',
            fontWeight: '600',
            color: isConnected ? '#34C759' : '#FF3B30'
          }}>
            {isConnected ? 'Connected' : 'Offline'}
          </span>
        </div>
      </header>

      <div style={{ maxWidth: '600px', margin: '0 auto', padding: '0 16px' }}>
        
        {/* Stats Cards */}
        <div style={{
          display: 'grid',
          gridTemplateColumns: '1fr 1fr',
          gap: '12px',
          marginTop: '16px',
          marginBottom: '24px'
        }}>
          <div style={{
            backgroundColor: '#FFFFFF',
            borderRadius: '12px',
            padding: '12px',
            textAlign: 'center'
          }}>
            <p style={{ fontSize: '13px', color: '#8E8E93', margin: '0 0 4px 0' }}>Total</p>
            <p style={{ fontSize: '28px', fontWeight: '700', color: '#000000', margin: 0 }}>{stats.total}</p>
          </div>
          <div style={{
            backgroundColor: '#FFFFFF',
            borderRadius: '12px',
            padding: '12px',
            textAlign: 'center'
          }}>
            <p style={{ fontSize: '13px', color: '#8E8E93', margin: '0 0 4px 0' }}>Completed</p>
            <p style={{ fontSize: '28px', fontWeight: '700', color: '#34C759', margin: 0 }}>{stats.done}</p>
          </div>
        </div>

        {/* Add Task Form */}
        <form onSubmit={handleAdd} style={{ marginBottom: '24px' }}>
          <div style={{
            display: 'flex',
            gap: '8px',
            backgroundColor: '#FFFFFF',
            borderRadius: '12px',
            padding: '8px',
            boxShadow: '0 1px 3px rgba(0, 0, 0, 0.08)'
          }}>
            <input 
              style={{
                flex: 1,
                padding: '10px 12px',
                borderRadius: '8px',
                border: 'none',
                outline: 'none',
                fontSize: '16px',
                color: '#000000',
                backgroundColor: '#F2F2F7'
              }}
              placeholder="Add a new task..."
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              disabled={!isConnected || loading}
            />
            <button
              type="submit"
              disabled={!isConnected || loading}
              style={{
                backgroundColor: isConnected ? '#007AFF' : '#C7C7CC',
                color: '#FFFFFF',
                border: 'none',
                padding: '10px 14px',
                borderRadius: '8px',
                cursor: isConnected ? 'pointer' : 'not-allowed',
                display: 'flex',
                alignItems: 'center',
                gap: '6px',
                fontWeight: '600',
                fontSize: '16px',
                transition: 'background-color 0.2s',
                opacity: loading ? 0.6 : 1
              }}
              onMouseEnter={(e) => {
                if (isConnected && !loading) e.target.style.backgroundColor = '#0051D5';
              }}
              onMouseLeave={(e) => {
                if (isConnected && !loading) e.target.style.backgroundColor = '#007AFF';
              }}
            >
              <Plus size={20} strokeWidth={3} />
            </button>
          </div>
        </form>

        {/* Filter Tabs */}
        <div style={{
          display: 'flex',
          gap: '8px',
          marginBottom: '16px',
          overflowX: 'auto',
          paddingBottom: '4px'
        }}>
          {['ALL', 'TODO', 'IN_PROGRESS', 'DONE'].map(f => (
            <button
              key={f}
              onClick={() => setFilterStatus(f)}
              style={{
                padding: '8px 16px',
                borderRadius: '20px',
                border: 'none',
                fontSize: '13px',
                fontWeight: '600',
                cursor: 'pointer',
                backgroundColor: filterStatus === f ? '#007AFF' : '#FFFFFF',
                color: filterStatus === f ? '#FFFFFF' : '#000000',
                transition: 'all 0.2s',
                whiteSpace: 'nowrap'
              }}
            >
              {f === 'ALL' ? 'All' : f === 'TODO' ? 'To Do' : f === 'IN_PROGRESS' ? 'In Progress' : 'Done'}
            </button>
          ))}
        </div>

        {/* Tasks List */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
          {filteredTasks.length === 0 ? (
            <div style={{
              padding: '48px 16px',
              textAlign: 'center',
              color: '#8E8E93'
            }}>
              <p style={{ fontSize: '16px', margin: 0 }}>
                {filterStatus === 'ALL' ? 'No tasks yet' : `No ${filterStatus.replace('_', ' ').toLowerCase()} tasks`}
              </p>
            </div>
          ) : (
            filteredTasks.map(task => {
              const status = getStatusInfo(task.status);
              return (
                <div
                  key={task.id}
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                    padding: '12px 16px',
                    backgroundColor: '#FFFFFF',
                    borderRadius: '12px',
                    boxShadow: '0 1px 3px rgba(0, 0, 0, 0.08)',
                    opacity: deleteAnimation === task.id ? 0 : 1,
                    transform: deleteAnimation === task.id ? 'translateX(100%)' : 'translateX(0)',
                    transition: 'all 0.3s ease-out'
                  }}
                >
                  <div style={{ display: 'flex', alignItems: 'center', gap: '12px', flex: 1 }}>
                    <div style={{
                      width: '20px',
                      height: '20px',
                      borderRadius: '50%',
                      backgroundColor: status.bgColor,
                      border: `2px solid ${status.color}`,
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      flexShrink: 0
                    }}>
                      {task.status === 'DONE' && (
                        <CheckCircle size={16} color={status.color} fill={status.color} />
                      )}
                    </div>
                    <div style={{ flex: 1, minWidth: 0 }}>
                      <p style={{
                        color: task.status === 'DONE' ? '#8E8E93' : '#000000',
                        fontWeight: '500',
                        margin: '0 0 2px 0',
                        fontSize: '16px',
                        textDecoration: task.status === 'DONE' ? 'line-through' : 'none'
                      }}>
                        {task.title}
                      </p>
                      <span style={{
                        fontSize: '12px',
                        color: status.color,
                        fontWeight: '600',
                        backgroundColor: status.bgColor,
                        padding: '2px 8px',
                        borderRadius: '8px',
                        display: 'inline-block'
                      }}>
                        {status.label}
                      </span>
                    </div>
                  </div>
                  <div style={{ display: 'flex', gap: '4px', alignItems: 'center' }}>
                    <select
                      value={task.status}
                      onChange={(e) => handleStatusChange(task.id, e.target.value)}
                      disabled={!isConnected}
                      style={{
                        padding: '6px 8px',
                        borderRadius: '6px',
                        border: 'none',
                        color: '#007AFF',
                        backgroundColor: '#F2F2F7',
                        cursor: isConnected ? 'pointer' : 'not-allowed',
                        fontSize: '13px',
                        fontWeight: '600'
                      }}
                    >
                      <option value="TODO">To Do</option>
                      <option value="IN_PROGRESS">Progress</option>
                      <option value="DONE">Done</option>
                    </select>
                    <button
                      onClick={() => handleDelete(task.id)}
                      disabled={!isConnected}
                      style={{
                        background: 'none',
                        border: 'none',
                        color: isConnected ? '#FF3B30' : '#C7C7CC',
                        cursor: isConnected ? 'pointer' : 'not-allowed',
                        padding: '6px 8px',
                        transition: 'color 0.2s'
                      }}
                      onMouseEnter={(e) => {
                        if (isConnected) e.target.style.color = '#CC0000';
                      }}
                      onMouseLeave={(e) => {
                        if (isConnected) e.target.style.color = '#FF3B30';
                      }}
                    >
                      <Trash2 size={16} strokeWidth={2.5} />
                    </button>
                  </div>
                </div>
              );
            })
          )}
        </div>
      </div>
    </div>
  );
}

export default App;

