window.onload=()=>{
    findAll();
}

function fnStore(){
    location.href='/mypage/storePage';
}

function fnStoreRegist(){
    var storeNameEl = document.getElementById("storeName");
    var storeLocationEl = document.getElementById("storeLocation");
    var storePhoneNumEl = document.getElementById("storePhoneNum");
    var storeInfoEl = document.getElementById("storeInfo");
    var fileEl = document.getElementById("fileSave");

    let storeForm = new FormData();

    if(fileEl.files.length !== 0){
        for(let i=0; i<fileEl.files.length; i++){
            storeForm.append("file", fileEl.files[i]);
        }
    }


    var data = {
        storeName : storeNameEl.value,
        storeLocation : storeLocationEl.value,
        storePhoneNum : storePhoneNumEl.value,
        storeInfo : storeInfoEl.value
    }

    // var toJson = new Blob([JSON.stringify(data)],{
    //     type : 'application/json',
    // });

    // storeForm.append("store", toJson);

    const headers = {
        params: {
            store: JSON.stringify(data)
        },
        headers : {
            'Content-Type': 'multipart/form-data',
        },
    };
    console.log(storeForm);

    axios.post('/api/v1/store/create', storeForm, headers)
    .then(response => {
        console.log(response);
    })
    .catch(error=>{
        console.log(error);
    });
}


function findAll(){

    axios.get('/api/v1/store/list').then(response => {
        const respData = response.data;
        const tableBody = document.getElementById("storeList");

        respData.forEach(data => {
            const row = document.createElement("tr");
            const numCell = document.createElement("td");
            const nameCell = document.createElement("td");
            const locationCell = document.createElement("td");
            const phoneCell = document.createElement("td");
            const infoCell = document.createElement("td");
            const gradeCell = document.createElement("td");
            const createdCell = document.createElement("td");
            const modifiedCell = document.createElement("td");

            const numLink = document.createElement("a");
            numLink.setAttribute("data-bs-toggle", "modal");
            numLink.setAttribute("data-bs-target", "#updateStore");

            nameCell.textContent = data.storeName;
            locationCell.textContent = data.storeLocation;
            phoneCell.textContent = data.storePhoneNum;
            infoCell.textContent = data.storeInfo;
            gradeCell.textContent = data.grade;
            createdCell.textContent = data.createDate;
            modifiedCell.textContent = data.modificationDate;


            numLink.textContent = data.storeNum;
            numLink.onclick="openModal()"



            numCell.appendChild(numLink);

            row.appendChild(numCell);
            row.appendChild(nameCell);
            row.appendChild(locationCell);
            row.appendChild(phoneCell);
            row.appendChild(infoCell);
            row.appendChild(gradeCell);
            row.appendChild(createdCell);
            row.appendChild(modifiedCell);
            tableBody.appendChild(row);
        })
    }).catch(error => {
        console.log(error);
    });
}
function fnStoreUpdate(){

}

function openModal() {
    var modal = document.getElementById('updateStore');
    var modalInput = document.getElementById('updateStoreName');

    // 모달이 열릴 때 input에 동적으로 지정된 기본값 설정
    modalInput.value = "test";

    modal.style.display = 'block';
}
