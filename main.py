from fastapi import FastAPI, HTTPException
from typing import Optional, List,Dict
from pydantic import BaseModel 

app = FastAPI()

class User(BaseModel):
    id: int
    name: str
    age: int

class All(BaseModel):
    id: int
    title: str
    body: str
    author: User

users = [
    {'id':1,'name':'Hoho', 'age': 24},
    {'id':2,'name':'Coco', 'age': 75},
    {'id':3,'name':'Lala', 'age': 43},
]


posts = [
    {'id': 1, 'title': 'News 1', 'body': 'Text 1','author':users[1]},
    {'id': 2, 'title': 'News 2', 'body': 'Text 2','author':users[0]},
    {'id': 3, 'title': 'News 3', 'body': 'Text 3','author':users[2]},
]

# @app.get("/items")
# async def get_all_items() -> list[All]:  
#     all_objects = []
#     for post in posts:
#         all_objects.append(All(id=post['id'], title=post['title'], body=post['body']))
#     return all_objects

@app.get("/items")
async def get_all_items() -> List[All]:
    return [All(**post) for post in posts]


@app.get("/items/{id}")
async def get_item_by_id(id: int) -> All:  
    for post in posts:
        if post['id'] == id:
            return All(**post)
    raise HTTPException(status_code=404, detail='Post Not')

@app.get("/search")
async def search(post_id: Optional[int] = None) -> Dict[str, Optional[All]]:
    if post_id:
        for post in posts:
            if post['id'] == post_id:
                return {"data": All(**post)}
        raise HTTPException(status_code=404, detail='Post Not')
    else:
        return {"data": None}
