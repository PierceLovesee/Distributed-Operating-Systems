# using the project from Caleb Curry as reference for building the project.
# https://www.youtube.com/watch?v=qbLc5a9jdXo

from flask import Flask, request
app = Flask(__name__)
from flask_sqlalchemy import SQLAlchemy

# Configure the database so we can connect to it
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///data.db'
db = SQLAlchemy(app)

# Model Objects we store in the DB
class Tutorial(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(80), unique=True, nullable=False)
    description = db.Column(db.String(120))

    def __repr__(self):
        return f"{self.name} - {self.description}"

@app.route('/')
def index():
    return 'Hello - Welcome to the RESTful API tutorials'

@app.route('/Tutorial')
def get_tutorials():
    tutorials = Tutorial.query.all()
    output = []
    for tutorial in tutorials:
        tutorial_data = {'name': tutorial.name, 'description': tutorial.description}
        output.append(tutorial_data)
    return {"Tutorials": output}


@app.route('/Tutorial/<id>')
def get_tutorial(id):
    tutorial = Tutorial.query.get_or_404(id)
    return {"name": tutorial.name, "description": tutorial.description}

@app.route('/Tutorial', methods=['POST'])
def add_tutorial():
    tutorial = Tutorial(name=request.json['name'], description=request.json['description'])
    db.session.add(tutorial)
    db.session.commit()
    return {'id': tutorial.id}

@app.route('/Tutorial/<id>', methods=['DELETE'])
def delete_tutorial(id):
    tutorial = Tutorial.query.get(id)
    if tutorial is None:
        return {"error": "not found"}
    db.session.delete(tutorial)
    db.session.commit()
    return {"message": ("Succesfully deleted ID " + id + " from database.")}

@app.route('/Tutorial/<id>', methods=['PUT'])
def update_tutorial(id):
    data = request.get_json()
    if 'name' not in data:
        return ({'error': 'Bad Request', 'message': 'Name a field must be present'}, 400)
    tutorial = Tutorial.query.get_or_404(id)
    tutorial.name = data['name']
    if 'description' in data:
        tutorial.description = data['description']
    db.session.commit()
    return {"id": tutorial.id, "name": tutorial.name, "description": tutorial.description}





    # tutorial = Tutorial.query.get(id)
    # form = Tutorial(obj=tutorial)
    # if tutorial is None:
    #     return({"error": "not found"})
    # if tutorial is not None:
    #     form.populate_obj(tutorial)
    #     db.session.add(tutorial)
    #     db.session.commit()
    #     return {"message": ("Succesfully updated ID " + id + "in database.")}
