*Tasks

- /users/{uid}  get  not found 404  10min  -4min
                get  success   200  7min -20min
                get  other user try to get 404 -7min -7min

- /users        post success   201 -7min 4min
                     invalid parameter 400 -7min 7min
                     email has been taken 400 -5min 10min
                     
- /users/{uid}/products/{pid} get not found 404 -7min