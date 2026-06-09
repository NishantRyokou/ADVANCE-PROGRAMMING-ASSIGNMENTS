import React, { useState } from 'react';

export default function CourseDashboard() {
    const [studentsMap, setStudentsMap] = useState(new Map());
    const [nameInput, setNameInput] = useState('');
    const [coursesInput, setCoursesInput] = useState('');
    const [gpaInput, setGpaInput] = useState('');
    const [selectedCourse, setSelectedCourse] = useState('');

    const addStudent = (e) => {
        e.preventDefault();
        if (!nameInput.trim() || !gpaInput) return;
        const newId = Date.now();
        const coursesSet = new Set(
            coursesInput.split(',')
                .map(c => c.trim())
                .filter(c => c.length > 0)
        );
        const newStudent = {
            id: newId,
            name: nameInput.trim(),
            enrolledCourses: coursesSet,
            gpa: parseFloat(gpaInput)
        };
        setStudentsMap(prev => new Map([...prev, [newId, newStudent]]));
        setNameInput('');
        setCoursesInput('');
        setGpaInput('');
    };

    const removeStudent = (id) => {
        setStudentsMap(prev => {
            const next = new Map([...prev].filter(([k]) => k !== id));
            return next;
        });
    };

    const studentsList = [...studentsMap.values()];
    const sortedStudents = [...studentsList].sort((a, b) => b.gpa - a.gpa);

    const allCoursesSet = studentsList.reduce((acc, student) => {
        student.enrolledCourses.forEach(course => acc.add(course));
        return acc;
    }, new Set());
    const uniqueCourses = [...allCoursesSet];

    const filteredStudents = selectedCourse
        ? sortedStudents.filter(student => student.enrolledCourses.has(selectedCourse))
        : sortedStudents;

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
            <h2>Course Enrollment Dashboard</h2>
            
            <form onSubmit={addStudent} style={{ marginBottom: '20px' }}>
                <input
                    type="text"
                    placeholder="Student Name"
                    value={nameInput}
                    onChange={(e) => setNameInput(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Courses (comma separated)"
                    value={coursesInput}
                    onChange={(e) => setCoursesInput(e.target.value)}
                />
                <input
                    type="number"
                    step="0.01"
                    placeholder="GPA"
                    value={gpaInput}
                    onChange={(e) => setGpaInput(e.target.value)}
                    required
                />
                <button type="submit">Add Student</button>
            </form>

            <div style={{ marginBottom: '20px' }}>
                <label>Filter by Course: </label>
                <select value={selectedCourse} onChange={(e) => setSelectedCourse(e.target.value)}>
                    <option value="">All Courses</option>
                    {uniqueCourses.map(course => (
                        <option key={course} value={course}>{course}</option>
                    ))}
                </select>
            </div>

            <div style={{ marginBottom: '20px' }}>
                <h3>Unique Enrolled Courses Across Batch</h3>
                {uniqueCourses.length === 0 ? <p>No courses enrolled</p> : (
                    <ul>
                        {uniqueCourses.map(course => (
                            <li key={course}>{course}</li>
                        ))}
                    </ul>
                )}
            </div>

            <h3>Student Registry (Sorted by GPA Descending)</h3>
            <table border="1" cellPadding="5" style={{ borderCollapse: 'collapse', width: '100%' }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Enrolled Courses</th>
                        <th>GPA</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredStudents.map(student => (
                        <tr key={student.id}>
                            <td>{student.id}</td>
                            <td>{student.name}</td>
                            <td>{[...student.enrolledCourses].join(', ')}</td>
                            <td>{student.gpa.toFixed(2)}</td>
                            <td>
                                <button onClick={() => removeStudent(student.id)}>Remove</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <div style={{ marginTop: '30px', padding: '15px', background: '#f9f9f9', border: '1px solid #ddd' }}>
                <h4>Complexity Analysis & Answers:</h4>
                <p><strong>Filtering students by course:</strong></p>
                <p>Time Complexity: O(S), where S is the number of students.</p>
                <p>Explanation: For each student in the list, checking if a course is present in their enrolledCourses Set takes O(1) time on average. Thus, filtering all students takes O(S) time.</p>
            </div>
        </div>
    );
}
