from fastapi import FastAPI, HTTPException
from typing import Optional
from pydantic import BaseModel 

app = FastAPI()

class All(BaseModel):
    id: int
    title: str
    body: str


posts = [
    {'id': 1, 'title': 'News 1', 'body': 'Text 1'},
    {'id': 2, 'title': 'News 2', 'body': 'Text 2'},
    {'id': 3, 'title': 'News 3', 'body': 'Text 3'},
]

@app.get("/items")
async def get_all_items() -> list:  
    return posts

@app.get("/items/{id}")
async def get_item_by_id(id: int) -> dict:  
    for post in posts:
        if post['id'] == id:
            return post
    raise HTTPException(status_code=404, detail='Post Not')

@app.get("/search")
async def search(post_id: Optional[int] = None) -> dict:
    if post_id:
        for post in posts:
            if post['id'] == post_id:
                return post
        raise HTTPException(status_code=404, detail='Post Not')
    else:
        return {"data": "No inf"}
