import os
from flask import Flask, request

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = "FILES"


@app.route("/", methods=['POST'])
def post():
    f = request.files['file']
    name = f.filename
    f.save(os.path.join(app.config['UPLOAD_FOLDER'], name))
    return "POST success"


@app.route("/get", methods=['GET'])
def get():
    return "GET success"


if __name__ == "__main__":
    app.run(host='0.0.0.0')