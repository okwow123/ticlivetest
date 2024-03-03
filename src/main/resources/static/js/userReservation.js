console.log('userReservation.js imported');

// 변수선언
let reservatedTable = document.getElementById('reservatedTable');
let reservationList = document.getElementById('reservationList');
console.log(reservatedTable);
alert(reservatedTable);

// 초기 실행되야할 함수

window.onload = () => {
  fetchReservation();
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
        let reservationDiv = document.createElement('div');
        reservationDiv.classList.add('reservationDiv');

        let storeInfos = {};
        storeInfos.append('reservationDate', data.reservationDate);
        //
        //날짜
        //시간
        //인원
        //전화번호
        //자세히보기

        // console.log(data);
        // alert(data);
      });
    });
};
