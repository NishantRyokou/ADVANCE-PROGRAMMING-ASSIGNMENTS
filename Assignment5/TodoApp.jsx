import React, { useState } from 'react';

export default function TodoApp() {
    const [todos, setTodos] = useState([]);
    const [input, setInput] = useState('');

    const addTodo = (e) => {
        e.preventDefault();
        if (input.trim() === '') return;
        setTodos([...todos, input.trim()]);
        setInput('');
    };

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
            <h2>Todo List</h2>
            <form onSubmit={addTodo}>
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    placeholder="Enter a todo"
                />
                <button type="submit">Add Todo</button>
            </form>
            <ul>
                {todos.map((todo, index) => (
                    <li key={index}>{todo}</li>
                ))}
            </ul>
        </div>
    );
}
