This is a fork of the original repository. To see the Pull Request history, go [here](https://github.com/csc301-fall-2022/team-project-16-sayyara-m/pulls?q=is%3Apr+is%3Aclosed).
### Team members:
Nathan Raymant (Frontend)

Hamoon Zamiri (Frontend)

Ahsan Saeed (Frontend)

Uthman Mohamad (Backend)

Haolin Fan (Backend)

# Sayyara

Sayyara is a web app that connects Vehicle Mechanics to Vehicle Owners.
It is a platform where Vehicle Mechanics can advertise their services and Vehicle Owners can find the best mechanics in their area.

### Deployed Applicaiton
- [Sayyara Website](https://sayyara-web.vercel.app/)
- [Backend Swagger Docs](https://sayyara-backend.up.railway.app/api/docs)
### Development Requirements

**For building and running the application you need:**
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Npm](https://nodejs.org/en/download/)

#### Build & Run application
###### Backend

Start the server:
```bash
# Start from root directory
cd backend
docker-compose up --build -d
```
The server listens on port `8080`. You can access it at http://localhost:8080/<endpoint-here>

Docs are available at http://localhost:8080/api/docs


Optionally test queries on the database in a terminal while the server is running with the command:
```bash
# After running the server
docker-compose exec postgres psql -U postgres
```

Stop the server:
```bash
docker-compose down
```

###### Frontend
```bash
# Start from root directory
cd frontend
npm install
npm run start
```
