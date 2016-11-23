*Tasks

- /users/{uid}  get  not found 404  10min  -4min
                get  success   200  7min -20min
                get  other user try to get 404 -7min -7min

- /users        post success   201 -7min 4min
                     invalid parameter 400 -7min 7min
                     email has been taken 400 -5min 10min
                     
- /users/{uid}/products/{pid} get not found 404 -7min 10min
                              get success 200 -7min 5min
                             
- /users/{uid}/products  post success 201 -7min 10min
                         invalid parameter 400 -7min 5min
                         get success 200 -7min 5min     
                         
- /users/{uid}/products/{pid} post change price success 200 -7min 5min
                                                invalid parameter 400 -5min 3min
                               
- /users/{uid}/orders/{oid} get  not found 404 -7min 5min
                                 success 200   -7min 4min
                                 
- /users/{uid}/orders  post success 201 -7min 5min
                            invalid parameter 400 -5min 3min
                            
- /users/{uid}/orders   get success 200 -5min 4min

- /users/{uid}/orders/{oid}/payment get not found 404 -5min 4min
                                        success  200 -5min 5min
                                        
- /users/{uid}/orders/{oid}/payment post success 201 -5min  5min

- /users/{uid}/orders/{oid}/refundRequests/{requestId} get not found 404 -5min 5min
                                                         success 200 -5min 4min
                                                         

- /users/{uid}/orders/{oid}/refundRequests post success 201 -5min 5min
                                               invalid parameter 400 -5min 3min
                                               
- /users/{uid}/orders/{oid}/refundRequests  get  success 200                                              
                                              
- /users/{uid}/orders/{oid}/refunds/{refundId} get not found 404 -5min 4min
                                                  success 200 -5min 3mn
                                                  
- /users/{uid}/orders/{oid}/refunds  post success 201 -5min 5min
                                          invalid parameter 400 -3min 3min
                                     get success 200 -5min 3min
                                               
                                               
