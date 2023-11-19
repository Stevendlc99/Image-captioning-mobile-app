from flask import Flask, request, jsonify, render_template
from PIL import Image
import sys
import os
import os
from werkzeug.utils import secure_filename
import numpy as np
from gtts import gTTS
from caption2 import CaptioningModel


app = Flask(__name__)



@app.route('/captionmodel', methods=["POST"])
def process_image():
    file = request.files['image']
    basepath=os.path.dirname(__file__)
    filename=secure_filename(file.filename)
    
    file_path = os.path.join(basepath, 'images', filename )
    file.save(file_path)

    # Read the image via file.streamX

    img2 = file_path
    modelo=CaptioningModel(img2)
    #captioning= modelo.response_model()
    #return captioning
    return jsonify({'message': modelo})


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)


