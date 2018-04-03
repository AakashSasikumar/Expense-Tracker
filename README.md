# Expense Tracker

Android App for tracking expenses

## Sprint 1

User should be able set category-wise spend limit and application should alert the user when the limits are breached.

- [x] Login Management
    - [x] Open the login/register page
    - [x] Enable logging-in [user-id, password, OTP (if applicable)]
    - [x] Upon successful login, redirect to the page intended
    - [x] Upon failure, redirect to exception processing (retry, forgot password or register)
- [x] Profile Management - Show the personal information collect and allow options for editing (name, address, DoB, current password, preferences)
- [ ] Settings Management
    - [x] Display the current list of categories and the maximum monthly spending limit under each category
    - [ ] Display the % of expense before alert is generated for each category and the incremental % for sending alerts
    - [x] Provide option to add new categories
    - [ ] Provide an option to list the common shops names under these categories
    - [ ] Type the pattern of the messages usually received from the card companies
- [ ] Show Analysis
    - [ ] Display the current list of categories and the date ranges for which data should be displayed
    - [ ] Display the transactions under each of these categories for this date range, allowing the user to 
        - [ ] Select the transaction to show the SMS message for that transaction
        - [ ] Move the transaction to a different category
        - [ ] Delete the transaction
        - [ ] Add a transaction manually or using a voice to text conversion (optional)
    - [ ] Draw a chart to show the distribution of the amount under various categories
    - [ ] Separately, indicate the amount spent under each category as a % for the current month and reset at the beginning of each month

## Sprint 2

Application should be able track the incoming messages, process it and classify them in four-five different categories

- [ ] If the application is not mobile app, use a 3rd party software that is capable of importing the SMS messages on a regular basis to the PC. If it is a mobile app, then the following can be done in the phone itself
- [ ] Remember the date and time of the last message scanned
- [ ] For all the new messages received after this time
    - [ ] Check if the message is an expense alert from a card, bank, PAYTM, etc. using pattern matching (regex)
    - [ ] If it is an expense alert, extract the date, shop name and transaction amount
    - [ ] Based on the shop name, assign the transaction to the categories defined
    - [ ] If the shop name is new, show a pop-up for the user to assign this shop name to a particular category
    - [ ] If the alert criteria for that category is breached, then send an alert to the user to make him/her aware of the spending limit

# LICENSE

This project is licensed under [Apache License 2.0](http://www.apache.org/licenses/). Check out the licensed details [here](https://github.com/Saumya-Rai/Expense-Tracker/blob/master/LICENSE).