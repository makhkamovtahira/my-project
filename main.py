from fastapi import FastAPI, HTTPException, Path, Query
from typing import Optional, List, Dict, Annotated
from pydantic import BaseModel, Field

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


class PostCreate(BaseModel):
    title: str
    body: str
    author_id: int


class Post(BaseModel):  
    id: int
    title: str
    body: str
    author: User

class UserCreate(BaseModel):
    name: Annotated[str, Field(..., description='Имя', min_length=2, max_length=20)]
    age: Annotated[int, Field(..., description='Возраст', ge=1, le=120)]


users = [
    {'id': 1, 'name': 'Hoho', 'age': 24},
    {'id': 2, 'name': 'Coco', 'age': 75},
    {'id': 3, 'name': 'Lala', 'age': 43},
]

posts = [
    {'id': 1, 'title': 'News 1', 'body': 'Text 1', 'author': users[1]},
    {'id': 2, 'title': 'News 2', 'body': 'Text 2', 'author': users[0]},
    {'id': 3, 'title': 'News 3', 'body': 'Text 3', 'author': users[2]},
]



@app.get("/items", response_model=List[All])
async def get_all_items():
    return [All(**post) for post in posts]



@app.post("/items/add", response_model=Post)
async def add_item(post: PostCreate):
    author = next((user for user in users if user['id'] == post.author_id), None)
    if not author:
        raise HTTPException(status_code=404, detail='User not found')

    new_post_id = len(posts) + 1
    new_post = {
        'id': new_post_id,
        'title': post.title,
        'body': post.body,
        'author': author
    }

    posts.append(new_post)
    return Post(**new_post)



@app.post("/user/add", response_model=User)
async def user_add(user: UserCreate):
    new_user_id = len(users) + 1
    new_user = {'id': new_user_id, 'name': user.name, 'age': user.age}
    users.append(new_user)
    return User(**new_user)




@app.get("/items/{id}", response_model=All)
async def get_item_by_id(
    id: Annotated[int, Path(..., description="Тута пишиться айди поста", ge=1, le=100)]
):
    for post in posts:
        if post['id'] == id:
            return All(**post)
    raise HTTPException(status_code=404, detail='Post Not Found')





@app.get("/search", response_model=Dict[str, Optional[All]])
async def search(post_id: Annotated[
    Optional[int],
    Query(description="ID поста, который нужно найти", ge=1, le=50)
] = None):
    if post_id:
        for post in posts:
            if post['id'] == post_id:
                return {"data": All(**post)}
        raise HTTPException(status_code=404, detail='Post Not Found')
    else:
        return {"data": None}
