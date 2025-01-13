from flask import Flask, jsonify, render_template
import mysql.connector

app = Flask(__name__)

# Database connection configuration
db_config = {
    'host': 'mysql',
    'user': 'root',       # Replace with your MySQL username
    'password': 'p1234',   # Replace with your MySQL password
    'database': 'netflix'          # Replace with your database name
}

# Serve the index.html page when visiting the root URL
@app.route('/')
def index():
    return render_template('index.html')

# API endpoint to fetch specific fields from the 'user' table
@app.route('/api/users', methods=['GET'])
def get_users():
    connection = None
    cursor = None
    try:
        # Establish connection
        connection = mysql.connector.connect(**db_config)
        cursor = connection.cursor(dictionary=True)

        # Execute the query
        cursor.execute('SELECT account_id, email FROM user')
        results = cursor.fetchall()

        # Check if results are empty
        if not results:
            return jsonify({'message': 'No data found'}), 404

        return jsonify(results)  # Return data as JSON
    except mysql.connector.Error as err:
        # Handle specific MySQL errors
        return jsonify({'error': f"MySQL Error: {err}"}), 500
    except Exception as e:
        # General error handling
        return jsonify({'error': f"Error: {str(e)}"}), 500
    finally:
        if cursor:
            cursor.close()
        if connection and connection.is_connected():
            connection.close()

# Run the server
if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
