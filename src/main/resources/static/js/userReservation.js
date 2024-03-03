alert('userReservation.js imported');

// 변수선언
let reservatedTable = document.getElementById('reservatedTable');
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
        console.log(data);
        alert(data);
      });
    });
};
