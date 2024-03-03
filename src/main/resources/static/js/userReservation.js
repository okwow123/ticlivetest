console.log('userReservation.js imported');

// 변수선언
let reservatedTable = document.getElementById('reservatedTable');
let reservationList = document.getElementById('reservationList');

let userInfo = null;
/** 삭제하려고하는 주요 예약에 관한 모든정보 파싱할필요x
 */
let reservationMainData = null;

console.log(reservatedTable);
// alert(reservatedTable);

// 초기 실행되야할 함수

window.onload = () => {
  fetchUserInfo();
  fetchReservation();
};

const fetchUserInfo = () => {
  fetch('/api/userInfo')
    .then((res) => {
      return res.json();
    })
    .then((data) => {
      userInfo = data;
      //   console.log(data);
    });
};

const fetchReservation = () => {
  console.log('fetchReservation진입');
  let url = '/api/v1/store/userReservation';
  let options = {
    method: 'GET',
  };

  fetch(url, options)
    .then((response) => {
      return response.json();
    })
    .then((datas) => {
      datas.forEach((data, idx) => {
        setReservatedDiv(data, reservationList, openReservationDetailModal);
      });
    });
};

// 매개변수 data,List,div에 부착할 함수

const setReservatedDiv = (data, ListDiv, divFunction) => {
  let reservationDiv = document.createElement('div');
  reservationDiv.classList.add('reservationDiv');
  reservationDiv.classList.add('row');
  reservationDiv.dataset.reservationInfo = JSON.stringify(data);

  // /** 예약자명  */
  // let reservationUserNameDiv = document.createElement('div');
  // reservationUserNameDiv.innerText = userInfo.userEmail;

  /* css 예정코드 아래 div들을 담아서 우측에 배치해야함 좌측에는 이미지 
        display: flex;
        flex-direction: column;
    */

  let reservationDiv_1 = document.createElement('div');
  reservationDiv_1.classList.add('col-3');

  let storeImg = document.createElement('img');
  if (data.store.images[0] != null && data.store.images[0] != '') {
    storeImg.setAttribute('src', data.store.images[0]);
  } else {
    storeImg.setAttribute('src', '/img/딸기.jpg');
  }
  storeImg.width = '100%';
  storeImg.height = '100%';

  reservationDiv_1.appendChild(storeImg);

  let reservationDiv_2 = document.createElement('div');
  reservationDiv_2.classList.add('col-9');

  let reservationDateDiv = document.createElement('div');
  reservationDateDiv.innerText = data.reservationDate;

  let reservationTimeDiv = document.createElement('div');
  reservationTimeDiv.innerText = data.reservationTime;

  let numberOfPersonDiv = document.createElement('div');
  numberOfPersonDiv.innerText = data.numberOfPerson + '명';

  let storePhoneNumDiv = document.createElement('div');
  storePhoneNumDiv.innerText = data.store.storePhoneNum;

  let storeInfoDiv = document.createElement('div');
  storeInfoDiv.innerText = data.store.storeInfo;

  reservationDiv_2.appendChild(reservationDateDiv);
  reservationDiv_2.appendChild(reservationTimeDiv);
  reservationDiv_2.appendChild(numberOfPersonDiv);
  reservationDiv_2.appendChild(storePhoneNumDiv);
  reservationDiv_2.appendChild(storeInfoDiv);

  reservationDiv.appendChild(reservationDiv_1);
  reservationDiv.appendChild(reservationDiv_2);

  ListDiv.appendChild(reservationDiv);

  reservationDiv.addEventListener('click', (e) => {
    divFunction(e.currentTarget);
  });
};

const openReservationDetailModal = (div) => {
  let data = JSON.parse(div.dataset.reservationInfo);
  setup_innerModalBody(data);
  console.log('modal.show()');
  let modal = new bootstrap.Modal(
    document.getElementById('reservationDetailModal')
  );
  modal.show();
};

const setup_innerModalBody = (data) => {
  console.log(data);
  let modal_body = document.getElementById('modalBody');
  modal_body.innerHTML = '';
  reservationMainData = data;
  setReservatedDiv(data, modal_body, null);
};

const cancel = () => {
  if (!confirm('정말로 예약을 취소하시겠습니까?')) {
    return;
  }
  alert('예약을 취소 중..');
  console.log('예약 메인정보');
  console.log(reservationMainData);
  let fetch_url =
    '/api/reservationCancel/' + reservationMainData.reservationNum;
  console.log(fetch_url);

  fetch(fetch_url, { method: 'DELETE' }).then((response) => {
    if (response.status === 200) {
      alert('예약이 취소되었습니다.');
      document.getElementById('ModalCloser').click();
    } else {
      alert('예약 취소가 실패하였습니다.');
    }
  });
};
