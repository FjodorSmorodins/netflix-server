// Fetch data from the Flask API
fetch('/api/users')  // This is the correct API endpoint
    .then(response => response.json())
    .then(data => {
        const tableBody = document.querySelector('#userTable tbody');
        // Loop through the data and add each row to the table
        data.forEach(row => {
            console.log(data)
            const tr = document.createElement('tr');
            tr.innerHTML = `
                        <td>${row.account_id}</td>
                        <td>${row.email}</td>
                    `;
            tableBody.appendChild(tr);
        });
    })
    .catch(error => console.error('Error fetching data:', error));