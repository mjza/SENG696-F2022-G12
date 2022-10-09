[:arrow_backward: Back to the previous menu :point_left:](./README.md)
<hr/>

# I.2. System Specifications

#### Table Of Contents :point_down:

[2. System Specifications](#2-system-specifications)
   - [2-1. Business Case](#2-1-business-case)
   - [2-2. System Description](#2-2-system-description)
   - [2-3. Assumption](#2-3-assumption)
   - [2-4. Requirements](#2-4-requirements)
   - [2-5. Wish List (Not implemented)](#2-5-wish-list-not-implemented)

<hr/>

## 2. System Specifications

In **system specifications document** we are going to speak about the five pieces of information. First we discuss what are the reasons that such a system is needed from the business perspective in the [Business Case](#2-1-business-case) section. Then, in the [System Description](#2-2-system-description) section, we describe the main features of the application. Later, in the [Assumption](#2-3-assumption) section, we discuss the pre-existing conditions that we assumed are there in place before starting the development. In the fourth part, actually [Requirements](#2-4-requirements) we reflect the whole requirements that we have received from the customer. Finally, in the [Wish List](#2-5-wish-list-not-implemented) section, we determine which part of requirements are going to be planed for the future revisions and are not going to be delivered in our implementation. 

### 2-1. Business Case

### 2-2. System Description

### 2-3. Assumption

### 2-4. Requirements

In this section, you can find the requirements. Each party (i.e. agent) has been coded by **bold** style. Each action/verb has been declared by *italic* style. Finally, each attribute has been identified by <ins>underline</ins> style.

1. Ability to *sign up* as **Provider** and **Client**.
2. Ability to be a **Guest** and *visit* the app.
3. For **Providers**: ability to *submit* <ins>name</ins>, <ins>website</ins>, <ins>logo</ins>, <ins>resume</ins>, <ins>special keywords</ins>, <ins>hourly compensation</ins>.
4. **Providers** *can get* a <ins>verified icon</ins> if they *send* their <ins>proof of business</ins> to **the System**. <br/>**The System** should *make sure* that every piece of information *is correct* and then *accept* the request.
5. For **Guests**: ability to *search* <ins>keywords</ins> and *get* a <ins>list of available Providers</ins>.
6. A <ins>contract</ins> should be *sent* to a **Provider** the moment they *sign up*. 
7. **Provider** should be able to *accept* or *reject* the <ins>contract</ins>.
8. Upon <ins>rejection</ins>, the **Provider** will be automatically *converted* to a **Client**, *losing* their <ins>resume</ins>, <ins>website</ins>, <ins>special keywords</ins> and <ins>hourly compensation information</ins>.
9.  When a **Guest** *visits* the **App**, they can only *see* the <ins>name</ins>, <ins>website</ins>, <ins>logo</ins>, <ins>resume</ins>, and <ins>special keywords</ins> of **Providers**. They *cannot see* their <ins>hourly compensation</ins>. Also, they *cannot place* a <ins>bid</ins> for the **Provider**.
10. Signed-up Agents (**Providers** and **Clients**) *can see* <ins>every piece of information</ins> available on **the system**.
11. **Providers** *can choose* between <ins>Basic</ins> and <ins>Premium plans</ins>. <br/><ins>Premium subscribers</ins> will *appear* first on <ins>the search list</ins>, regardless of their <ins>approval ratings</ins> or <ins>hourly compensation</ins>.
12. The <ins>sorting algorithm</ins> always *puts* <ins>Premium</ins> **Providers** on top, then <ins>verified</ins> **Providers**, and then the rest. Between each group, **Providers** should be *sorted* based on their <ins>approval ratings</ins> by default (can be changed).
13. A **Client** is able to *change* the sorting of <ins>results</ins> upon searching a <ins>keyword</ins> (e.g. **Clients’** <ins>approvals</ins>, <ins>number of projects done</ins>, the <ins>amount of hourly compensation</ins>).
14. A **Client** *can request* a **Provider** and *place* a <ins>bid</ins>. The <ins>bid</ins> *can be a different* <ins>value</ins> than <ins>the hourly compensation</ins> of the **Provider**.
15. **Provider** *can accept* or *reject* a <ins>bid</ins>.
16. A *rejection* from the **Provider** will be directly *sent* to the **Client**.
17. *Accepting* a <ins>request</ins> from the **Provider** will go through **the System** first, and not directly to the **Client**.
18. **The System**, upon *receiving* an <ins>accept confirmation</ins>, will *pull up* a <ins>contract</ins> and *send* it to both **Provider** and **Client**.
19. **Provider** and **Client** *can accept* or *reject* the <ins>contract</ins>.
20. Any *money transfer* will be *handled* by **the System**. <br/> **The System** will *receive* 30% of any <ins>transaction</ins>. This <ins>info</ins> should *be* in the <ins>contract</ins>.
21. Ability to *watch* the <ins>progress</ins> of the project for the both sides (**Provider** and **Client**).
22. The <ins>tracking page</ins> will *show* the <ins>tentative deadline</ins>, <ins>progress so far</ins>, and <ins>estimated time of completion</ins> based on the current pace.
23. A <ins>chat room page</ins> will *be created* for **Client** and **Provider** once a project *gets accepted*.
24. Any <ins>change request</ins> from the **Client** must first *get accepted* by the **Provider** after a project <ins>begins</ins>. <ins>Deadlines</ins> could *change* based on **Provider’s** request
25. When a project *is done*, the **Client** *can leave* a <ins>comment</ins> and <ins>rating</ins> for the **Provider**,
26. A **Provider** *can also leave* a <ins>comment</ins> and <ins>rating</ins> for the **Client**. **Provider**s *can see* the <ins>past ratings</ins> of a **Client** when there is a <ins>new bid</ins>.
27. The app **must** have a <ins>GUI<ins>.

### 2-5. Wish List (Not implemented)
It seems we can implement all requirements, except the number 21 till 26. Basically, it means we assume all parties are satisfied from each other and there is no need for monitoring or rating. Therefore, we hope to be able to implement items 1 to 20 and also number 27 that requests a mandatory GUI. And we put items 21 to 26 in our wish list. 