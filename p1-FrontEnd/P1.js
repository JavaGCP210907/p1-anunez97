// database url
const url =          "http://localhost:8090/"

// uri endpoints
const loginReq =     "login"
const logoutReq =    "logout"
const e_PendingReq = "employee/pending"
const e_HistoryReq = "employee/history"
const e_SubmitReq =  "employee/submit"
const m_HistoryReq = "manager/history"
const m_PendingReq = "manager/pending"
const m_ApproveReq = "manager/approve"
const m_DenyReq =    "manager/deny"

// when false the table will show pending requests
var showPending = false;

// set up event listeners
document.getElementById("login-btn").addEventListener("click", loginFunc)
document.getElementById("logout-btn").addEventListener("click", logoutFunc)
document.getElementById("employee-submit-btn").addEventListener("click", e_SubmitFunc)
document.getElementById("manager-approve-btn").addEventListener("click", m_ApproveFunc)
document.getElementById("manager-deny-btn").addEventListener("click", m_DenyFunc)
document.getElementById("manager-pending-check").addEventListener("click", m_SwitchPending)
document.getElementById("employee-pending-check").addEventListener("click", e_SwitchPending)

async function loginFunc() {

    //get the user input
    let usern = document.getElementById("username").value
    let userp = document.getElementById("password").value

    // create a js object with the username and password
    let user = {
        username:usern,
        password:userp
    }

    console.log(user)

    // send a POST request to the server
    let response = await fetch(url + loginReq, {

        method:"POST",
        body:JSON.stringify(user),
        credentials:"include"
    });

    console.log(response)

    // if the response is successful
    if(response.status === 200) {
        let u = document.getElementById("username")
        let p = document.getElementById("password")

        u.value = ""
        p.value = ""

        //* hide the login textboxes and button ------------------------------------------------------------------------------
        u.style.display="none"
        p.style.display="none"
        document.getElementById("login-btn").style.display="none"
        //*/

        //* display the logout button ------------------------------------------------------------------------------------------
        document.getElementById("logout-btn").style.display="inline"
        //*/

        let data = await response.json() // get the response data (user role)

        let user = {
            user_role:data.userRole,
            user_first_name:data.userFirstName,
            user_last_name:data.userLastName
        }
        console.log(user)

        document.getElementById("login-header").innerHTML = "Welcome " + user.user_first_name + " " + user.user_last_name + "!" // change the login header to welcome user

        document.getElementById("reimbursement-table").style.display="inline"

        showPending = false

        // TODO depending on the user role display the appropriate buttons
        if(user.user_role == "Employee") {
            document.getElementById("employee-options").style.display="inline"
            document.getElementById("submit-form").style.display="inline"
            document.getElementById("employee-pending-check").checked = false

            e_ShowTable()
        }
        else if(user.user_role == "Manager") {
            document.getElementById("manager-options").style.display="inline"
            document.getElementById("approve-request").style.display="inline"
            document.getElementById("deny-request").style.display="inline"
            document.getElementById("manager-pending-check").checked = false
            document.getElementById("reimb-approve-id").value=""
            document.getElementById("reimb-deny-id").value=""

            m_ShowTable()
        }

        document.getElementById("submit-message").innerText=""
        
    }
    else {
        document.getElementById("login-header").innerText="Login failed!"
    }
}

async function logoutFunc() {

    let response = await fetch(url + logoutReq, {
        method:"POST",
        body:"Logging out ...",
        credentials:"include"
    })

    if(response.status === 200) {

        // display the login prompts
        document.getElementById("login-btn").style.display="inline" 
        document.getElementById("username").style.display="inline"
        document.getElementById("password").style.display="inline" 

        // hide the stuff
        document.getElementById("employee-options").style.display="none"
        document.getElementById("manager-options").style.display="none"
        document.getElementById("reimbursement-table").style.display="none"
        document.getElementById("logout-btn").style.display="none"
        document.getElementById("submit-form").style.display="none"
        document.getElementById("approve-request").style.display="none"
        document.getElementById("deny-request").style.display="none"

        let amount = document.getElementById("reimb-amount");
        let desc = document.getElementById("reimb-desc");
        let type = document.getElementById("reimb-type");

        document.getElementById("login-header").innerText="Please Enter Your Login Credentials!"

        amount.value=""
        desc.value=""
        type.value=""

        clearReimbs()

        document.getElementById("submit-message").innerText=""

    }
}

async function e_PendingFunc() {

    let response = await fetch(url + e_PendingReq, {credentials:"include"})

    if(response.status === 200) {
        
        console.log(response)

        let data = await response.json()

        displayReimb(data)
    }
}

async function e_HistoryFunc() {

    let response = await fetch(url + e_HistoryReq, {credentials:"include"})

    if(response.status === 200) {
        
        console.log(response)

        let data = await response.json()

        displayReimb(data)
    }
}

async function e_SubmitFunc() {

    let inputtype = document.getElementById("reimb-type")
    let inputamount = document.getElementById("reimb-amount")

    if(isNaN(inputtype.value) || isNaN(inputamount.value)) {
        document.getElementById("submit-message").innerText="Expecting a number as an input!"

        return
    }

    let amount = document.getElementById("reimb-amount");
    let desc = document.getElementById("reimb-desc");
    let type = document.getElementById("reimb-type");

    let request = {
        reimb_amount:amount.value,
        reimb_description:desc.value,
        reimb_type_id_fk:type.value
    }

    let response = await fetch(url + e_SubmitReq, {
        method:"POST",
        body:JSON.stringify(request),
        credentials:"include"
    })

    if(response.status === 200) {
        document.getElementById("submit-message").textContent="Request submitted sucessfully!"

        amount.value=""
        desc.value=""
        type.value=""

        e_ShowTable()
    }
    else {
        document.getElementById("submit-message").textContent="Problem submitting request!";
    }
}

async function m_PendingFunc() {

    let response = await fetch(url + m_PendingReq, {credentials:"include"})

    if(response.status === 200) {
        
        console.log(response)

        let data = await response.json()

        displayReimb(data)
    }
}

async function m_HistoryFunc() {

    let response = await fetch(url + m_HistoryReq, {credentials:"include"})

    if(response.status === 200) {
        
        console.log(response)

        let data = await response.json()

        displayReimb(data)
    }
}

async function m_ApproveFunc() {

    let id = document.getElementById("reimb-approve-id").value
        
    let request = {
        reimb_id:id
    }

    let response = await fetch(url + m_ApproveReq, {
        method:"PATCH",
        body:JSON.stringify(request),
        credentials:"include"
    })

    if(response.status === 200) {
        console.log(response)

        document.getElementById("reimb-approve-id").value=""
        document.getElementById("submit-message").innerText="Request approved!"
        m_ShowTable()
    }
    else {

        let data = await response.json();

        let message = {
            mes:data.errorMessage
        }

        document.getElementById("submit-message").innerText=message.mes
    }
}

async function m_DenyFunc() {

    let input = document.getElementById("reimb-deny-id")

    if(isNaN(input.value)) {
        document.getElementById("submit-message").innerText="Expecting a number as an input!"

        return
    }

    let id = document.getElementById("reimb-deny-id").value

    let request = {
        reimb_id:id
    }

    let response = await fetch(url + m_DenyReq, {
        method:"PATCH",
        body:JSON.stringify(request),
        credentials:"include"
    })

    if(response.status === 200) {
        console.log(response)

        document.getElementById("reimb-deny-id").value=""
        document.getElementById("submit-message").innerText="Request denied!"

        m_ShowTable()
    }
    else {
        let data = await response.json();

        let message = {
            mes:data.errorMessage
        }
        
        document.getElementById("submit-message").innerText=message.mes
    }
}

function displayReimb(data) {

    clearReimbs()

    let body = document.getElementById("reimbursement-body")

    for(let reimb of data) {

        let row = document.createElement("tr"); // create a table row

        let cellID = document.createElement("td");
        cellID.innerHTML = reimb.reimb_id; // fill the cell with the appropriate reimbursement data
        row.appendChild(cellID); // add the td element to the tr element

        let cellAmount = document.createElement("td");
        cellAmount.innerHTML = parseFloat(reimb.reimb_amount).toFixed(2)
        row.appendChild(cellAmount);

        let cellSubmitted = document.createElement("td");
        cellSubmitted.innerHTML = reimb.reimb_submitted;
        row.appendChild(cellSubmitted);

        let cellResolved = document.createElement("td");
        cellResolved.innerHTML = reimb.reimb_resolved;
        row.appendChild(cellResolved);

        let cellDesc = document.createElement("td");
        cellDesc.innerHTML = reimb.reimb_description;
        row.appendChild(cellDesc);

        let cellAuthor = document.createElement("td");
        cellAuthor.innerHTML = reimb.reimb_author_fk;
        row.appendChild(cellAuthor);

        let cellResolver = document.createElement("td");
        cellResolver.innerHTML = reimb.reimb_resolver_fk;
        row.appendChild(cellResolver);

        let cellStatus = document.createElement("td");
        cellStatus.innerHTML = reimb.reimb_status_id_fk;
        row.appendChild(cellStatus);

        let cellType = document.createElement("td");
        cellType.innerHTML = reimb.reimb_type_id_fk;
        row.appendChild(cellType);

        body.appendChild(row);
    }
}

function clearReimbs() {
    let body = document.getElementById("reimbursement-body")

    // find better way to do this
    // removes all the rows in the table body
    body.innerHTML = ""
}

function e_SwitchPending() {
    showPending = !showPending

    e_ShowTable()
}

function m_SwitchPending() {
    showPending = !showPending

    m_ShowTable()
}

function e_ShowTable() {

    if(showPending) {
        e_PendingFunc()
    }
    else {
        e_HistoryFunc()
    }
}

function m_ShowTable() {

    if(showPending) {
        m_PendingFunc()
    }
    else {
        m_HistoryFunc()
    }
}