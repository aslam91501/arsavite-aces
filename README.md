# Java-Spring-boot-login-registration

### Postman (Sid's version): https://restless-meadow-610762.postman.co/workspace/Personal-Workspace~ff5b262f-27b0-44df-bf16-b085406d5109/collection/5129081-1863c877-63d9-45dd-9447-d688dcc50aae?action=share&creator=5129081
### Postman (Amal's version): https://lunar-sunset-580153.postman.co/workspace/My-Workspace~3944c7c1-380a-4aab-887f-621d604e20da/collection/29097792-07363831-4e73-487b-b22e-72f630a83998?action=share&creator=29097792


| Method | Endpoint                  | Description                   |
|--------|---------------------------|-------------------------------|
| POST   | `/api/auth/signup`        | Signup new account            |
| POST   | `/api/auth/signin`        | Login an account              |
| POST   | `/api/auth/signout`       | Logout the account            |
| GET    | `/api/test/all`           | Retrieve public content       |
| GET    | `/api/test/user`          | Access User’s content         |
| GET    | `/api/test/mod`           | Access Moderator’s content    |
| GET    | `/api/test/admin`         | Access Admin’s content        |
| GET    | `/bands/all`              | Access every band information |
| PUT    | `/bands/1`                | Update band info              |
| POST   | `/bands/create`           | Create a new band             |

Most param's are available in the postman ; Feel free to add or remove params :)



| Request Type | Endpoint                | Description                            |
| ------------ | ----------------------- | -------------------------------------- |
| POST         | `/bands/create`         | Create a new band.                     |
| POST         | `/bands/follow`         | Follow a band.                         |
| POST         | `/bands/unfollow`       | Unfollow a band.                       |
| GET          | `/bands/all`            | Retrieve a list of all bands.          |
| GET          | `/bands/{id}`           | Retrieve a specific band by ID.        |
| GET          | `/bands/{id}/followers` | Retrieve a list of band followers.     |
| PUT          | `/bands/update/{id}`    | Update the details of a specific band. |
| DELETE       | `/bands/delete/{id}`    | Delete a specific band by ID.          |


