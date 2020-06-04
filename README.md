# Kanbaned Chain Liana Tasks ( KCLTs ) < Renamed To Liana Tasks Since The Name Was Too Long >
> Liana Tasks is a collaboration tool that organizes your projects into boards. It is free, flexible, and visual way to manage your projects and organize anything in kanban boards and you can manage groupe projects by inviting other users to the kanban board.
> For Fun : KCLTs, Kanbaned Chain Liana Tasks, Inspired from essential BCCAS, kanbaned for kanban, chain, we all know what does that mean, liana for the big liana tree and tasks, you know it already.

## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
Using Liana Tasks users will be able to:

    Have A Sustainable, Paper Less Environment

    Improve Collaboration

    Plan Resource Allocation And Availability

    Save 24000+ Hours A Year

Aside from the time-saving aspect, I think it’s having everything in one place, to make sure that bits of paper don’t get lost, emails don’t get deleted, well, stuff gets deleted out of ep as well, but really not the way an email can slip through the cracks, right? It made the workflow more efficient. The end result is that we’re actually making more stuff done instead of less.

We recently implemented one of our processes directly into easy projects. It eliminated a process that we ran through spreadsheets. We estimate that it will save us 1-2 hours per project just in set-up time. The customizable nature of easy projects is what really helped improve our workflow. It is highly adaptable. If an internal processes changes, we’re confident that easy projects will be able to adapt. If something doesn’t work for you right out of the box, 9 times out of 10 you will be able to make a few easy customizations to solve your issue.

Sara who was giving a hole view, idea about Liana Tasks said :  It's really helpful when it comes to project management. I liked that you can set deadlines and be able to know which card/assignment to prioritize and finish within the given time frame. Plus it is free and it doesn't ask many of your personal information.

Andrai also said : Their board is simply gorgeous, I can't believe there are such talented ux / ui. I can't wait to start using it since it doesn't ask for my credit card information, but this is more than enough for us and our needs.

## Screenshots
![KCLTs Home Page](https://github.com/Sed9yDataBank/Kanbaned-Chain-Liana-Tasks/blob/master/KCLTS%20Screenshots/First.gif)

![KCLTs Login](https://github.com/Sed9yDataBank/Kanbaned-Chain-Liana-Tasks/blob/master/KCLTS%20Screenshots/Login.gif)

![KCLTs Boards](https://github.com/Sed9yDataBank/Kanbaned-Chain-Liana-Tasks/blob/master/KCLTS%20Screenshots/Boards.gif)

![KCLTs Tasks](https://github.com/Sed9yDataBank/Kanbaned-Chain-Liana-Tasks/blob/master/KCLTS%20Screenshots/Tasks.gif)

## Technologies

For Frontend
* Angular 
* Bootstrap
* NGX Bootstrap

For Backend
* SpringBoot
* JPA 
* Hibernate
* PostgreSQL
* Postman
* AWS Cloud Services
* JWT
* Gmail API


## Setup

You can clone this repository and setup the frontend, server and provide your own postgres credentials, aws and gmail api tokens.

Why no live demo ? deploying this project is going to cost money for a good hosting service and frontend is still not completed.

## Code Examples

```java
//Business Logic In Service Layer Of Adding The Board Background Image To The AWS S3 Bucket And Saving Its Path To The Data Base. 

 @Override
    @Transactional
    public void saveBoardImage(UUID boardId, MultipartFile file) {
        isFileEmpty(file);
        isImage(file);

        BoardDTO board = getBoardOrThrow(boardId);
        Map<String, String> metadata = extractMetadata(file);

        String path = String.format("%s/%s", BucketName.BACKGROUND_IMAGE.getBucketName(), board.getBoardId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            board.setBackgroundImagePath();
        } catch (IOException e) {
            throw new ApiRequestException(board.getBackgroundImagePath() + " Could Not Be Saved");
        }
    }
 ``` 

```java
 @PostMapping("/signin")
//Login Authentication Controller With JWT
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UUID userId = userRepository.getIdByUsername(userDetails.getUsername());
        JWTResponse jwtRes = new JWTResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(), userId);
        return ResponseEntity.ok(jwtRes);
    }
 ``` 

## Features

* Simple kanban board to manage tasks and subtasks.
* Board members invitation system for collaborative projects.
* Status and deadlines management.
* Blog section to get more in depth on how to use kanban boards more efficiently.

To-do List:
* Finish coding the frontend
* Add setting and history changes menu in boards

## Status
Project Is: _in progress_, Currentley Developing The Frontend Part 

## Inspiration
Project Inspired By Sedki Benzid. Based On Building A Easy, Free To Use Task Management Tool

## Contact
Created by [@Sed9yDataBank](https://github.com/Sed9yDataBank) - Feel Free To Contact Me ! [benzidsedki@gmail.com]
