let reservationDateTableUp_btn = document.getElementById(
  'reservationDateTableUp_btn'
);
let reservationDateTable = document.getElementById('reservationDateTable');

/** 유동으로 조사하는 값 (달력에서 예약을 희망하는 날짜) */
let currentDate = new Date(); 

/** 절대 바뀌지 않는 오늘의 값 */
const realToday = new Date(); 
/** 어느 가게를 선택했는지 담고있는 변수 {*} @type {number} */
let storeNum = -1;
let storeInfo = null;

/** modal_body전역 변수 */
let modal_body = document.getElementById('modal-body');

/** search시 다른 메뉴 div의 display속성을 임시저장 */
let previousDisplayStyle;

/** 예약 시 몇 명 예약을 희망했는지 담는 전역 변수 */
let selectedPerson = null;

/** 예약 시 몇 시에 예약을 희망했는지 담는 전역 변수 */
let selectedTime = null;

//검색 할때 사용됨(함수명 리팩토링 필요)
async function fetchList() {
  //검색으로 가져온 목록들
  let search_option = document.getElementById('search_option').value;
  let search_input = document.getElementById('search_input').value;
  console.log('path만들때 사용할 변수값들');
  console.log(search_option);
  console.log(search_input);
  let fetch_path = '/api/' + search_option + '/' + search_input;

  let category_list = document.getElementById('category_list');
  let searched_list = document.getElementById('searched_list');

  if (search_input == '') {
    category_list.style.display = previousDisplayStyle;
    searched_list.style.display = 'none';
    return;
  }

  if (category_list.style.display != 'none') {
    previousDisplayStyle = category_list.style.display;
    category_list.style.display = 'none';
    searched_list.style.display = previousDisplayStyle;
  }

  console.log('검색으로 fetch 시도할 주소 : ' + fetch_path);
  try {
    const response = await fetch(fetch_path);
    if (response.status != 200) {
      return;
    }
    console.log('조회해온 데이터 : ' + response);
    console.log(response);
    const stores = await response.json();

    console.log('json으로 만든 데이터 : ' + stores);
    console.log(stores);
    stores.forEach((store,idx) => {
      // console.log(store);
      let store_div = document.createElement('div');
      store_div.classList.add('store_div');
      store_div.dataset.store = JSON.stringify(store);
      console.log(store);

      let images = store.images.map((image, idx) => {
        if (idx == 0 && image == null) return '이미지 없음 경로';
        else if (image == null) {
          return;
        }
      });
      // let store_name = store.storeName;
      // let store_path = store.storeLocation;

      let images_div = document.createElement('div');
      let store_info_div = document.createElement('div');
      let store_name_div = document.createElement('div');
      let store_path_div = document.createElement('div');

      images_div.classList.add('images_div');
      store_info_div.classList.add('store_info_div');
      store_name_div.classList.add('store_name_div');
      store_path_div.classList.add('store_path_div');

      let images_img = document.createElement('img');
      images_img.setAttribute('src', '/img/딸기.jpg');
      //images_img.setAttribute("src", images[0]);
      images_img.setAttribute('alt', '이미지 경로 유실');
      images_img.style.width = images_div.offsetWidth;
      images_img.style.height = images_div.offsetWidth;
      images_div.offsetHeight = images_div.offsetWidth;
      images_div.appendChild(images_img);

      store_name_div.innerText = store.storeName;
      store_path_div.innerText = store.storeLocation;

      let storeNum_div = document.createElement('input');
      storeNum_div.setAttribute('type', 'hidden');
      storeNum_div.setAttribute('value', store.storeNum);

      store_div.appendChild(images_div);
      store_div.appendChild(store_info_div);
      store_info_div.appendChild(store_name_div);
      store_info_div.appendChild(store_path_div);
      store_div.appendChild(storeNum_div);

      store_div.classList.add('row');
      images_div.classList.add('col-3');
      store_info_div.classList.add('col');
      store_info_div.classList.add('col-9');
      store_name_div.classList.add('row-3');
      store_path_div.classList.add('row-3');
      
      /** 모달을 열기위한 이벤트 추가 */
      store_div.addEventListener('click', (e) => {
        storeNum = e.currentTarget.querySelector('input[type="hidden"]').value;
        storeInfo = e.currentTarget.dataset.store;
        console.log('store_div눌림 storeNum 갱신 to ' + storeNum);
      });

      store_div.addEventListener('click', function () {
        // 모달 버튼을 클릭하는 것처럼 모달을 엽니다.
        var modal = new bootstrap.Modal(
          document.getElementById('exampleModal')
        );
        setupCalendar();
        modal.show();
      });

      searched_list.appendChild(store_div);
    }); //모든 store_div 세팅완료

    let images_divs = document.querySelectorAll('.images_div');
    images_divs.forEach((images_div) => {
      images_div.style.height = images_div.offsetWidth + 'px';
      let img = images_div.querySelector('img');
      img.style.width = '100%';
      img.style.height = '100%';
    });
  } catch (error) {
    //여기가 undefined error?
    //console.log(error);
    console.log('조회된 데이터가 없습니다.');
    let nothing_searched_div = document.createElement('div');

    nothing_searched_div.classList.add('nothing_searched_div');
    nothing_searched_div.innerHTML = '조회된 데이터가 없습니다.';

    document.getElementById('searched_list').appendChild(nothing_searched_div);
  }
}

//modal로부터 호출됨 -> setupCalendar -> setupReservableTimes
const setupCalendar = (inputDate) => {
  // console.log(modal_body);
  console.log('setupCalendar감지 / 입력된 inputDate값');
  console.log(inputDate);

  console.log('modal body 클리어');
  modal_body.innerHTML = '';
  // console.log(modal_body);

  //원하는 시간으로 테이블 변경하기 위한 기본 조건
  currentDate = new Date(); //예약하기 위한 타겟이 되는 날짜
  if (inputDate !== undefined) {
    currentDate = inputDate;
    console.log('다음 값 갱신');
  }

  //테이블 상단부의 오늘로 가는 버튼 + <년+월> 버튼 및 알림구현
  let tableHeader = document.createElement('div');
  tableHeader.classList.add('tableHeader');
  tableHeader.classList.add('row');

  //정의
  let goTodayBtn = document.createElement('button');
  let leftArrow = document.createElement('div');
  let rightArrow = document.createElement('div');
  let showingYearAndMonth = document.createElement('div');

  // 배치 상세
  goTodayBtn.classList.add('col-3');
  leftArrow.classList.add('col-2');
  rightArrow.classList.add('col-2');
  showingYearAndMonth.classList.add('col-5');

  //구현상세
  goTodayBtn.innerHTML = '오늘';
  goTodayBtn.addEventListener('click', () => {
    // currentDate = new Date(); setupCalendar()내부에 이미 있는 코드
    setupCalendar();
  });

  leftArrow.innerText = '<';
  leftArrow.classList.add('arrow');
  leftArrow.addEventListener('click', () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    setupCalendar(currentDate);
    //showingYearAndMonth이쪽의 글자도 갱신해줘야함
    //showingYearAndMonth를 갱신하는 메서드도 필요
  });

  rightArrow.innerText = '>';
  rightArrow.classList.add('arrow');
  rightArrow.addEventListener('click', () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    setupCalendar(currentDate);
  });

  showingYearAndMonth.innerText =
    currentDate.getFullYear() + '년 ' + (currentDate.getMonth() + 1) + '월';

  //부착
  tableHeader.appendChild(goTodayBtn);
  tableHeader.appendChild(leftArrow);
  tableHeader.appendChild(showingYearAndMonth);
  tableHeader.appendChild(rightArrow);

  modal_body.appendChild(tableHeader);

  // 주어진 조건의 달이 첫번째 요일이 무슨요일로 시작하는지 계산
  // 주어진 조건의 1달전의 몇개의 요일이 필요한지 계산
  firstDayOfMonth = getFirstDayOfMonth();
  console.log(firstDayOfMonth);
  let previousMonthDays = firstDayOfMonth;
  let needPreviousMonthDays = true;

  //테이블의 제일 좌측 상단의 시간 (년월일포함);
  //테이블 이 기준이 되는값

  /** @todo 왼쪽의 값이 currentDate가 아닌 currentDate의 1일 날짜를 넣어야함 */
  let sameMonth1stDate = new Date(currentDate);
  sameMonth1stDate.setDate(1);

  let mainDate = getDateNDaysAgo(sameMonth1stDate, previousMonthDays);
  console.log(mainDate.toDateString);

  // 달력 요소 생성
  var calendarTable = document.createElement('table');
  var calendarHead = document.createElement('thead');
  var calendarBody = document.createElement('tbody');

  let count = 0;
  let iterateDate;
  //thead 세팅 구현중
  var row = calendarHead.insertRow();
  row.classList.add('row');
  for (var j = 0; j < 7; j++) {
    var cell = row.insertCell();
    cell.classList.add('col');
    cell.innerHTML = '' + getTodayDay(j);
  }

  //tbody 세팅
  for (var i = 0; i < 6; i++) {
    //행이 6개
    var row = calendarBody.insertRow();
    row.classList.add('row');
    for (var j = 0; j < 7; j++) {
      //열이 7개 일월화수목금토
      iterateDate = getDateNDaysNext(mainDate, count);
      
      var cell = row.insertCell();
      cell.classList.add('col');
      var cellElement = document.createElement('div');
      cellElement.dataset.date = iterateDate.toString();
      if (iterateDate < realToday) {
        //현실기준 과거시점은 예약불가
        cellElement.innerHTML = iterateDate.getDate();
        cellElement.classList.add('unavailableDate');
      } else if (iterateDate.toDateString >= realToday.toDateString) {
        if (iterateDate.toDateString() == currentDate.toDateString()) {
          cellElement.classList.add('currentDate');
        }
        //현실보다 미래시점 예약가능 //오늘이지만 과거시점의 예외처리 필요
        cellElement.addEventListener('click', (e) => {
          let myDateString = e.currentTarget.dataset.date;
          console.log(myDateString);
          setupCalendar(
            new Date(myDateString)
          );
        });
        // 아래코드가 그 아래 세줄 코드로 변경
        // cellElement.innerText = iterateDate.getDate();

        let spanInCell = document.createElement('span');
        spanInCell.innerText = iterateDate.getDate();
        cellElement.appendChild(spanInCell);
        
        //이쪽에 저 a link가 눌렸을때 아래 예약가능시간 탭도 갱신해주는 코드 추가필요;
        cellElement.classList.add('availableDate');
      }
      cell.appendChild(cellElement);
      count++;
    }
  }
  // 생성된 달력 요소를 reservationDateTable에 추가
  calendarTable.appendChild(calendarHead);
  calendarTable.appendChild(calendarBody);
  modal_body.style.textAlign = 'center';
  modal_body.appendChild(calendarTable);

  //예약할 명수 추가할 리스트 띄어주기
  let reservableList = document.createElement('div');
  reservableList.classList.add('reservableList');
  reservableList.classList.add('row');

  for(let i=1;i<13;i++){
    //요소마다의 별개 연산이 들어가면 좋음
    // 너무적은인원의 예약과 많은인원의 예약의 경우
    // 다른 function으로 연결하여서 그function내에서 세부 나눔처리
    let reservablePerson = document.createElement('div');
    let spanInReservablePerson = document.createElement('span');
    reservablePerson.classList.add('reservablePerson');
    reservablePerson.classList.add('col');
    reservablePerson.dataset.personCount = i;
    reservablePerson.addEventListener('click',(e)=>{
      let pressedPerson = e.currentTarget;
      if(selectedPerson){
        selectedPerson.classList.remove('selectedPerson');
        selectedPerson.classList.remove('selected');
      }
      pressedPerson.classList.add('selectedPerson');
      pressedPerson.classList.add('selected');
      selectedPerson = pressedPerson;
    })

    spanInReservablePerson.innerText=i+'명';
    spanInReservablePerson.classList.add('spanInReservablePerson');

    reservablePerson.appendChild(spanInReservablePerson);
    reservableList.appendChild(reservablePerson);
  }
  modal_body.appendChild(reservableList);

  //예약가능한 시간들 나타내주기

  // reservationDateTable.appendChild(calendarTable);
  setupReservableTimes(currentDate);
};

/**
 * 예약가능한 시간때를 list에 담아 modal_body에 부착하는 메서드
 * @param {Date} currentDate 예약가능한 시간을 조회할 날짜
 */
const setupReservableTimes = (currentDate) => {

  var currentString =
    currentDate.getFullYear() +
    '-' +
    (currentDate.getMonth() + 1) +
    '-' +
    currentDate.getDate();
  // fetch("/api/v1/store/" + storeNum + "/" + currentDateJson)
  console.log('fetch할때 가져갈 storeNum값 : ' + storeNum);
  console.log('/api/v1/store/에 접근 시도');
  fetch('/api/v1/store/' + storeNum + '/' + currentString)
  .then((data) => {
    return data.json();
  })
  .then((datas) => {
    let reservableTimesList = document.createElement('div');
    datas.forEach((data) => {
      let reservableTime = document.createElement('div');
      reservableTime.classList.add('reservableTime');
      reservableTime.classList.add('col');
      reservableTime.addEventListener('click',(e)=>{
        let pressedTime = e.currentTarget;
        if(selectedTime){
          selectedTime.classList.remove('selectedTime');
          selectedTime.classList.remove('selected');
        }
        pressedTime.classList.add('selectedTime');
        pressedTime.classList.add('selected');
        selectedTime = pressedTime;
      });
      
      /** @todo A href 달아야함*/
      reservableTime.innerText = data;
      reservableTime.dataset.time = data;

      // console.log(data);
      reservableTimesList.appendChild(reservableTime)
    });

    reservableTimesList.classList.add('reservableTimesList');
    reservableTimesList.classList.add('row');

    modal_body.appendChild(reservableTimesList);
  });
};

// 날짜 관련 함수 모음
function getTodayDay(dayIndex) {
  const days = ['일', '월', '화', '수', '목', '금', '토'];
  if (typeof dayIndex != 'number') {
    return;
  }
  return days[dayIndex % 7];
}

// firstDayOfMonth는 지역변수 인것같은데..
// 전역변수인 currentDate에 영향이 있었음
function getFirstDayOfMonth() {
  let tempDate = new Date(currentDate)
  tempDate.setDate(1);
  return tempDate.getDay();
}

function getDateNDaysAgo(standardDate, n) {
  var pastDate = new Date(standardDate.getTime() - n * 24 * 60 * 60 * 1000);
  return pastDate;
}

function getDateNDaysNext(standardDate, n) {
  var nextDate = new Date(standardDate.getTime() + n * 24 * 60 * 60 * 1000);
  return nextDate;
}

// 하나의 div 세팅
function setup_recommend() {
  // 고정 추천리스트 이미지와 함께 검색으로 연결되어야함
  let recommend_list = document.getElementById('recommend_list');
  let dataJSON = [
    //추후에는 외부에서 이 값을 읽어와야함 //외부에서 주입되는 고정추천리스트
    {
      itemname: '마카롱',
      image: '/img/마카롱.jpg', //이미지 경로
      tags: ['마카롱', '태그2'],
      linkedurl: '',
      storeNum: 0,
    },
    {
      itemname: '딸기',
      image: '/img/딸기.jpg', //이미지 경로
      tags: ['딸기', '태그2'],
      linkedurl: '',
      storeNum: 1,
    },
    {
      itemname: '마카롱',
      image: '/img/마카롱.jpg', //이미지 경로
      tags: ['마카롱', '태그2'],
      linkedurl: '',
      storeNum: 2,
    },
    {
      itemname: '딸기',
      image: '/img/딸기.jpg', //이미지 경로
      tags: ['딸기', '태그2'],
      linkedurl: '',
      storeNum: 3,
    },
    {
      itemname: '마카롱',
      image: '/img/마카롱.jpg', //이미지 경로
      tags: ['마카롱', '태그2'],
      linkedurl: '',
      storeNum: 4,
    },
    {
      itemname: '딸기',
      image: '/img/딸기.jpg', //이미지 경로
      tags: ['딸기', '태그2'],
      linkedurl: '',
      storeNum: 5,
    },
  ];

  dataJSON.forEach((item, idx) => {
    let item_div = document.createElement('div'); //recommend_list에 부착될 요소들
    item_div.classList.add('item_div');
    item_div.style.width = '9vw';
    item_div.style.height = item_div.style.width;

    let item_img = document.createElement('img'); //item_div에 담길 요소
    item_img.setAttribute('src', item.image);
    item_img.classList.add('item_img');

    let tags_div = document.createElement('div'); //item_div에 담길 요소
    tags_div.classList.add('col');
    item.tags.forEach((tag, tagidx) => {
      let tag_div = document.createElement('div');
      tag_div.classList.add('tag_div');
      tag_div.style.display = 'inline-block';
      tag_div.innerText = '#' + tag;
      tags_div.appendChild(tag_div);
    });

    item_div.appendChild(item_img);
    item_div.appendChild(tags_div);

    recommend_list.appendChild(item_div);
  });
}

// 하나의 div 세팅
function setup_hashtags() {
  let hashtags = [
    //외부에서 유저 정보를 통계를 내서 주입해주는 리스트
    {
      tagname: '태그1',
      url: '',
    },
    {
      tagname: '태그2',
      url: '',
    },
    {
      tagname: '태그3',
      url: '',
    },
    {
      tagname: '태그4',
      url: '',
    },
  ];

  let hashtag_list = document.getElementById('hashtag_list');
  hashtags.forEach((tag_obj, idx) => {
    let a = document.createElement('a');
    a.href = tag_obj.url;
    a.innerText = '#' + tag_obj.tagname;
    a.style.border = '1px solid #ff3d00';
    a.style.borderRadius = '18px';
    a.style.textDecoration = 'none';
    a.style.color = 'black';
    a.style.padding = '1px';

    hashtag_list.appendChild(a);
  });
}

const reserve = () =>{
  if(!selectedPerson){
    alert('예약 인원수를 설정해주세요');
    return;
  }

  if(!selectedTime){
    alert('희망 예약 시간을 설정해주세요');
    return;
  }
  let storeName = JSON.parse(storeInfo).storeName;
  let storeNum = JSON.parse(storeInfo).storeNum;
  let numberOfPerson = selectedPerson.dataset.personCount;
  let reservationDate = currentDate.toDateString();
  let reservationTime = selectedTime.dataset.time;

  console.log(reservationDate);
  console.log(currentDate.toISOString());
  alert(reservationDate)
  alert(currentDate.toISOString());

  /** 
   *  @todo fetch필요 storeNum으로 store 정보 불러오기
   *  @todo store을 만들떄 dataset에 store를 담기
   */
  if(confirm('다음과 같이 예약 하시겠습니까?'+'\n'
  +'예약 가게명 : ' + storeName +'\n'
  +'예약 날짜 : ' + reservationDate +'\n'
  +'예약 시간 : ' + reservationTime +'\n'
  +'예약 인원수 : ' + numberOfPerson +'\n')){
    let data = {
      storeNum,
      numberOfPerson,
      reservationDate:currentDate.toISOString(),
      reservationTime,
    }
    const options = {
      method: "POST",
      Headers:{
        "Content-Type" : "application/json",        
      },
      body : JSON.stringify(data),
    }
    alert('예약 진행중'); //여기에 방어로직추가
    //api로 예약을 하고 status.ok가 뜰때와 아닐때의 예외처리
    fetch('/api/v1/store/reservation',options)
      .then(response => {
        if(response.status===200){
          alert("예약이 완료 되었습니다."+'\n'
          +'예약 가게명 : ' + storeName +'\n'
          +'예약 날짜 : ' + reservationDate +'\n'
          +'예약 시간 : ' + reservationTime +'\n'
          +'예약 인원수 : ' + numberOfPerson +'\n')

          // 위 행동 이후에 모달을 내려줘야함
          // 예약 가능한 시간을 재 조회해야함

          const closeButton = document.getElementById("exampleModal-closer");
          closeButton.click();
        }else{
          throw new Error("Request failed with status code " + response.status);
        }
      })
      .catch((error) => {
        if(error.response){
          const response = error.response;
          response.json().then(data=>{
            alert(data.message);
          })
          console.error(data.message);
        }
        console.error(error.message);
      });
  } else {
    alert('예약 신청을 취소하였습니다.');
  }
}


// 코드 확인 후 삭제 필요
// 숨긴 div에서 modal 방식으로 변경되어서 필요없는 코드
// function defaultSetting() {
//   var parent = document.getElementById('contactme');
//   reservationDateTable.style.left = parent.offsetLeft + 'px';
//   reservationDateTable.style.width = parent.offsetWidth + 'px';
//   reservationDateTable.style.backgroundColor = '#f1f1f1';
// }

// 페이지 로딩 후 시작 함수를 모아둔곳
// window.onload 변경고려
function setup() {
  setup_recommend();
  setup_hashtags();
  // defaultSetting();
}

// reservationDateTableUp_btn.addEventListener('click', showReservableTable);

// document.getElementById('cancelReserving').addEventListener('click', () => {
//   reservationDateTable.style.display = 'none';
// });

// // 삭제해도 ok?
// // modal로 변경됨으로 추정
// const showReservableTable = async (e) => {
//   console('showReservableTable 함수 시작');
//   reservationDateTable.style.display = 'block';
//   let main = e.currentTarget;
//   console.log(main);
//   if (
//     e.currentTarget.querySelector('input[type="hidden"]') != null &&
//     storeNum != e.currentTarget.querySelector('input[type="hidden"]').value
//   ) {
//     storeNum = await e.currentTarget.querySelector('input[type="hidden"]')
//       .value;
//     console.log('갱신된 storeNum : ');
//     console.log(storeNum);
//     currentDate = new Date();
//     console.log('갱신된 currentDate : ');
//     console.log(currentDate);
//     console.log('showReservableTable 중 정상적인 갱신완료 추정');
//   } else {
//     console.log('비상');
//     console.log(e.currentTarget.querySelector('input[type="hidden"]') != null);
//     console.log(
//       storeNum != e.currentTarget.querySelector('input[type="hidden"]').value
//     );
//     console.log(
//       'e.currentTarget.querySelector("input[type="hidden"]") : ' +
//         e.currentTarget.querySelector('input[type="hidden"]')
//     );
//     console.log(
//       'e.currentTarget.querySelector("input[type="hidden"]").value : ' +
//         e.currentTarget.querySelector('input[type="hidden"]').value
//     );
//     console.log('storeNum:' + storeNum);
//   }
//   setupCalendar();
// };
