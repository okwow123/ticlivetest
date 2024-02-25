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

    

    for(let i=0; i<=fileEl.length; i++){
        storeForm.append("file", fileEl.files[i]);
        console.log(fileEl.files[i]);
    }

    var data = {
        storeName : storeNameEl.value,
        storeLocation : storeLocationEl.value,
        storePhoneNum : storePhoneNumEl.value,
        storeInfo : storeInfoEl.value
    }

    var toJson = new Blob([JSON.stringify(data)],{
        type : 'application/json',
    });

    storeForm.append("store", toJson);

    const headers = {
        headers : {
            'Content-Type': 'multipart/form-data',
        },
    };

    axios.post('/api/v1/store/create', storeForm, headers);
}
