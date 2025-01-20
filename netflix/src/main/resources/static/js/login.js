document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // Prevent default form submission

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const apiEndpoint = 'http://134.209.199.147:8082/api/users/login';

    try {
        const response = await fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        });

        // Check if the response is JSON
        const contentType = response.headers.get('Content-Type');
        if (!response.ok || !contentType || !contentType.includes('application/json')) {
            const responseText = await response.text(); // Fallback for non-JSON error
            console.error('Error response text:', responseText);
            alert(`Login failed: ${responseText || 'Unknown error occurred'}`);
            return;
        }

        // Parse JSON response
        const data = await response.json();

        if (data.token) {
            localStorage.setItem('token', data.token); // Save the token

            // Redirect based on role
            switch (data.role) {
                case 'JUNIOR':
                    window.location.href = '../html/junior-dashboard.html';
                    break;
                case 'MEDIOR':
                    window.location.href = '../html/medior-dashboard.html';
                    break;
                case 'SENIOR':
                    window.location.href = '../html/senior-dashboard.html';
                    break;
                case 'VIEWER':
                    window.location.href = '../html/viewer-dashboard.html';
                    break;
                default:
                    alert('Unexpected role. Please contact support.');
                    console.error(`Unexpected role: ${data.role}`);
            }
        } else {
            alert('Login failed: Missing authentication token.');
        }
    } catch (error) {
        console.error('Error during login:', error);
        alert('An error occurred while trying to log in. Please try again later.');
    }
});
