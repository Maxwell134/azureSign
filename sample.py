from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/')
def james():
    return '<h2>Welcome to my World</h2>'
@app.route('/health')
def main():
    return jsonify({'status': 200})

@app.route('/live')
def index():
    return jsonify({'status': 200})

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=80)
