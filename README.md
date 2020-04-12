# Kanbaned Chain Liana Tasks ( KCLTs )
> KCLTs Is A Collaboration Tool That Organizes Your projects Into Boards. It Is Free, Flexible, And Visual Way To Manage Your Projects And Organize Anything In Kanban Boards And You Can Manage Groupe Projects By Inviting Other Users To The Kanban Board.
> KCLTs, Kanbaned Chain Liana Tasks, Inspired With Essential BCCAs, Kanbaned For Kanban, Chain, We All Know What Does That Mean, Liana For The Big Liana Tree And Tasks, You Know It Already.

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
With The Implementation Of Kanbaned Chain Liana Tasks, Users Will Be Able To:

    Have A Sustainable, Paper Less Environment

    Improve Collaboration

    Plan Resource Allocation And Availability

    Save 24000+ Hours A Year

Aside From The Time-saving Aspect, I Think It’s Having Everything In One Place, To Make Sure That Bits Of Paper Don’t Get Lost, Emails Don’t Get Deleted, Well, Stuff Gets Deleted Out Of Ep As Well, But Really Not The Way An Email Can Slip Through The Cracks, Right? It Made The Workflow More Efficient. The End Result Is That We’re Actually Making More Stuff Done Instead Of Less.

We Recently Implemented One Of Our Processes Directly Into Easy Projects. It Eliminated A Process That We Ran Through Spreadsheets. We Estimate That It Will Save Us 1-2 Hours Per Project Just In Set-up Time. The Customizable Nature Of Easy Projects Is What Really Helped Improve Our Workflow. It Is Highly Adaptable. If An Internal Processes Changes, We’re Confident That Easy Projects Will Be Able To Adapt. If Something Doesn’t Work For You Right Out Of The Box, 9 Times Out Of 10 You Will Be Able To Make A Few Easy Customizations To Solve Your Issue.

Sara Who Was Giving A Hole View, Idea About Kanbaned Chain Liana Tasks Said :  It's Really Helpful When It Comes To Project Management. I Liked That You Can Set Deadlines And Be Able To Know Which Card/Assignment To Prioritize And Finish Within The Given Time Frame. Plus It Is Free And It Doesn't Ask Many Of Your Personal Information.

Andrai Also Said : Their Board Is Simply Gorgeous, I Can't Believe There Are Such Talented UX / UI. I Can't Wait To Start Using It Since It Doesn't Ask For My Credit Card Information, But This Is More Than Enough For Us And Our Needs.

## Screenshots
![KCLTs Home Page](https://github.com/Sed9yDataBank/Kanbaned-Chain-Liana-Tasks/blob/master/KCLTS%20Screenshots/First.gif)

![KCLTs Login](https://github.com/Sed9yDataBank/Kanbaned-Chain-Liana-Tasks/blob/master/KCLTS%20Screenshots/Login.gif)

![KCLTs Boards](https://github.com/Sed9yDataBank/Kanbaned-Chain-Liana-Tasks/blob/master/KCLTS%20Screenshots/Boards.gif)

![KCLTs Tasks](https://github.com/Sed9yDataBank/Kanbaned-Chain-Liana-Tasks/blob/master/KCLTS%20Screenshots/Tasks.gif)

## Technologies

For Frontend
* Angular ( For Non-Technical Viewers Of This Project, HTML, CSS, JavaScript, TypeScript )
* Bootstrap
* NGX Bootstrap

For Backend
* SpringBoot ( For Non-Technical Viewers Of This Project, J2EE Java )
* JPA 
* Hibernate
* PostgreSQL
* Postman
* AWS S3 Cloud Services
* Authentication With JWT
* Gmail API


## Setup

Describe how to install / setup your local environement / add link to demo version.

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

* Simple Kanban Board To Manage Tasks And Subtasks
* Board Members Invitation For Collaborative Projects
* Status And Deadlines Management For Tasks And SubTask
* Blog Section To Manage Your Boards Better

To-do List:
* Finish Coding The Frontend
* Add Setting And History Changes Menu In Boards

## Status
Project Is: _in progress_, Currentley Developing The Frontend Part To Connect With THe Features Added In Backend

## Inspiration
Project Inspired By Sedki Benzid. Based On Building A Easy, Free To Use Task Management Tool

## Contact
Created by [@Sed9yDataBank](https://github.com/Sed9yDataBank) - Feel Free To Contact Me ! [benzidsedki@gmail.com]
