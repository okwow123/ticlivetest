let reservationDateTableUp_btn = document.getElementById(
  "reservationDateTableUp_btn"
);
let reservationDateTable = document.getElementById("reservationDateTable");
let currentDate = new Date(); //유동으로 조사하는 값
const realToday = new Date(); //절대 바뀌지 않는 오늘의 값
let storeNum=-1;
let modal_body = document.getElementById('modal-body');

let previousDisplayStyle;

async function fetchList() {
  //검색으로 가져온 목록들
  let search_option = document.getElementById("search_option").value;
  let search_input = document.getElementById("search_input").value;
  console.log('path만들때 사용할 변수값들');
  console.log(search_option);
  console.log(search_input);
  let fetch_path = "/api/" + search_option + "/" + search_input;

  let category_list = document.getElementById("category_list");
  let searched_list = document.getElementById("searched_list");

  if (search_input == "") {
    category_list.style.display = previousDisplayStyle;
    searched_list.style.display = "none";
    return;
  }

  if (category_list.style.display != "none") {
    previousDisplayStyle = category_list.style.display;
    category_list.style.display = "none";
    searched_list.style.display = previousDisplayStyle;
  }

  console.log("검색으로 fetch 시도할 주소 : "+fetch_path);
  try {
    const response = await fetch(fetch_path);
    if(response.status != 200){
      return;
    }
    console.log("조회해온 데이터 : "+response);
    console.log(response);
    const stores = await response.json();

    console.log("json으로 만든 데이터 : " + stores);
    console.log(stores);
      stores.forEach((store) => {
        // console.log(store);
        let store_div = document.createElement("div");
        store_div.classList.add("store_div");

        let images = store.images.map((image, idx) => {
          if (idx == 0 && image == null) return "이미지 없음 경로";
          else if (image == null) {
            return;
          }
        });
        // let store_name = store.storeName;
        // let store_path = store.storeLocation;

        let images_div = document.createElement("div");
        let store_info_div = document.createElement("div");
        let store_name_div = document.createElement("div");
        let store_path_div = document.createElement("div");

        images_div.classList.add("images_div");
        store_info_div.classList.add("store_info_div");
        store_name_div.classList.add("store_name_div");
        store_path_div.classList.add("store_path_div");
        

        let images_img = document.createElement("img");
        images_img.setAttribute("src", "");
        //images_img.setAttribute("src", images[0]);
        images_img.setAttribute("alt", "이미지 경로 유실");
        images_img.style.width = images_div.offsetWidth;
        images_img.style.height = images_div.offsetWidth;
        images_div.offsetHeight = images_div.offsetWidth;
        images_div.appendChild(images_img);

        store_name_div.innerText = store.storeName;
        store_path_div.innerText = store.storeLocation;

        let storeNum_div = document.createElement("input");
        storeNum_div.setAttribute("type", "hidden");
        storeNum_div.setAttribute("value", store.storeNum);

        store_div.appendChild(images_div);
        store_div.appendChild(store_info_div);
        store_info_div.appendChild(store_name_div);
        store_info_div.appendChild(store_path_div);
        store_div.appendChild(storeNum_div);

        store_div.classList.add("row");
        images_div.classList.add('col-3');
        store_info_div.classList.add('col');
        store_info_div.classList.add('col-9');
        store_name_div.classList.add('row-3');
        store_path_div.classList.add('row-3');
        
        // images_div.classList.add('col-sm-2');
        // store_info_div.classList.add('col-sm-10');
        // store_name_div.classList.add('row-sm-3');
        // store_path_div.classList.add('row-sm-3');

        // store_div.addEventListener("click", showReservableTable);
          
        // 타겟 요소를 클릭했을 때 모달을 열기 위한 이벤트 리스너를 추가합니다.
        store_div.addEventListener('click',(e)=>{
          storeNum = e.currentTarget.querySelector('input[type="hidden"]').value;
          console.log('store_div눌림 storeNum 갱신 to '+storeNum);
        })
        store_div.addEventListener('click', function() {
          // 모달 버튼을 클릭하는 것처럼 모달을 엽니다.
          var modal = new bootstrap.Modal(document.getElementById('exampleModal'));
          setupCalendar();
          modal.show();
        });

        searched_list.appendChild(store_div);
      });//모든 store_div 세팅완료

    let images_divs = document.querySelectorAll('.images_div');
    images_divs.forEach((images_div)=>{
      images_div.style.height = images_div.offsetWidth+'px';
      let img = images_div.querySelector('img');
      img.style.width = '100%';
      img.style.height = '100%';
    })
  }
  catch(error){
    //여기가 undefined error?
    //console.log(error);
    console.log("조회된 데이터가 없습니다.");
    let nothing_searched_div = document.createElement("div");

    nothing_searched_div.classList.add("nothing_searched_div");
    nothing_searched_div.innerHTML = "조회된 데이터가 없습니다.";

    document
    .getElementById("searched_list")
    .appendChild(nothing_searched_div);   
  }

}

function setup_recommend() {
  // 고정 추천리스트 이미지와 함께 검색으로 연결되어야함
  let recommend_list = document.getElementById("recommend_list");
  let dataJSON = [
    //추후에는 외부에서 이 값을 읽어와야함 //외부에서 주입되는 고정추천리스트
    {
      itemname: "마카롱",
      image: "/img/마카롱.jpg", //이미지 경로
      tags: ["마카롱", "태그2"],
      linkedurl: "",
      storeNum: 0,
    },
    {
      itemname: "딸기",
      image: "/img/딸기.jpg", //이미지 경로
      tags: ["딸기", "태그2"],
      linkedurl: "",
      storeNum: 1,
    },
    {
      itemname: "마카롱",
      image: "/img/마카롱.jpg", //이미지 경로
      tags: ["마카롱", "태그2"],
      linkedurl: "",
      storeNum: 2,
    },
    {
      itemname: "딸기",
      image: "/img/딸기.jpg", //이미지 경로
      tags: ["딸기", "태그2"],
      linkedurl: "",
      storeNum: 3,
    },
    {
      itemname: "마카롱",
      image: "/img/마카롱.jpg", //이미지 경로
      tags: ["마카롱", "태그2"],
      linkedurl: "",
      storeNum: 4,
    },
    {
      itemname: "딸기",
      image: "/img/딸기.jpg", //이미지 경로
      tags: ["딸기", "태그2"],
      linkedurl: "",
      storeNum: 5,
    },
  ];

  dataJSON.forEach((item, idx) => {
    let item_div = document.createElement("div"); //recommend_list에 부착될 요소들
    item_div.classList.add("item_div");
    item_div.style.width = "9vw";
    item_div.style.height = item_div.style.width;

    let item_img = document.createElement("img"); //item_div에 담길 요소
    item_img.setAttribute("src", item.image);
    item_img.classList.add("item_img");

    let tags_div = document.createElement("div"); //item_div에 담길 요소
    tags_div.classList.add("col");
    item.tags.forEach((tag, tagidx) => {
      let tag_div = document.createElement("div");
      tag_div.classList.add("tag_div");
      tag_div.style.display = "inline-block";
      tag_div.innerText = "#" + tag;
      tags_div.appendChild(tag_div);
    });

    item_div.appendChild(item_img);
    item_div.appendChild(tags_div);

    recommend_list.appendChild(item_div);
  });
}

function setup_hashtags() {
  let hashtags = [
    //외부에서 유저 정보를 통계를 내서 주입해주는 리스트
    {
      tagname: "태그1",
      url: "",
    },
    {
      tagname: "태그2",
      url: "",
    },
    {
      tagname: "태그3",
      url: "",
    },
    {
      tagname: "태그4",
      url: "",
    },
  ];

  let hashtag_list = document.getElementById("hashtag_list");
  hashtags.forEach((tag_obj, idx) => {
    let a = document.createElement("a");
    a.href = tag_obj.url;
    a.innerText = "#" + tag_obj.tagname;
    a.style.border = "1px solid #ff3d00";
    a.style.borderRadius = "18px";
    a.style.textDecoration = "none";
    a.style.color = "black";
    a.style.padding = "1px";

    hashtag_list.appendChild(a);
  });
}

function setup() {
  setup_recommend();
  setup_hashtags();
}

function defaultSetting() {
  var parent = document.getElementById("contactme");
  reservationDateTable.style.left = parent.offsetLeft + "px";
  reservationDateTable.style.width = parent.offsetWidth + "px";
  reservationDateTable.style.backgroundColor = "#f1f1f1";
}
defaultSetting();

 const showReservableTable = async (e) => {
  console('showReservableTable 함수 시작');
  reservationDateTable.style.display = "block";
  let main = e.currentTarget;
  console.log(main);
  if (
    e.currentTarget.querySelector('input[type="hidden"]') != null &&
    storeNum != e.currentTarget.querySelector('input[type="hidden"]').value
  ) {
    storeNum = await e.currentTarget.querySelector('input[type="hidden"]').value;
    console.log("갱신된 storeNum : ");
    console.log(storeNum);
    currentDate = new Date();
    console.log("갱신된 currentDate : ");
    console.log(currentDate);
    console.log("showReservableTable 중 정상적인 갱신완료 추정");
  } else {
    console.log("비상");
    console.log(e.currentTarget.querySelector('input[type="hidden"]') != null);
    console.log(
      storeNum != e.currentTarget.querySelector('input[type="hidden"]').value
    );
    console.log(
      'e.currentTarget.querySelector("input[type="hidden"]") : ' +
        e.currentTarget.querySelector('input[type="hidden"]')
    );
    console.log(
      'e.currentTarget.querySelector("input[type="hidden"]").value : ' +
        e.currentTarget.querySelector('input[type="hidden"]').value
    );
    console.log("storeNum:" + storeNum);
  }
  setupCalendar();
};

reservationDateTableUp_btn.addEventListener("click", showReservableTable);

document.getElementById("cancelReserving").addEventListener("click", () => {
  reservationDateTable.style.display = "none";
});

//setupCalendar로부터 호출됨
const setupReservableTimes = (currentDate) => {
  const reservableTimes_div = document.createElement("div");
  reservableTimes_div.classList.add("reservableTimes_div");
  var currentDateJson = JSON.stringify({
    year: currentDate.getFullYear(),
    month: currentDate.getMonth(),
    day: currentDate.getDate(),
  });
  var currentString = ""+currentDate.getFullYear()+"-"+(currentDate.getMonth()+1)+"-"+currentDate.getDate();
  // fetch("/api/v1/store/" + storeNum + "/" + currentDateJson)
  console.log("fetch할때 가져갈 storeNum값 : " + storeNum);
  console.log("/api/v1/store/에 접근 시도");
  fetch("/api/v1/store/" + storeNum + "/" + currentString)
    .then((data) => {
      return data.json();
    })
    .then((datas) => {
      datas.forEach((data) => {
        console.log(data);
      });
    });
};

//modal로부터 호출됨
const setupCalendar = (inputDate) => {
  
  modal_body.innerHTML = "";

  //원하는 시간으로 테이블 변경하기 위한 기본 조건
  currentDate = new Date(); //예약하기 위한 타겟이 되는 날짜
  if (inputDate !== undefined) {
    currentDate = inputDate;
    console.log('다음 값 갱신');
    console.log(currentDate);
  }

  //테이블 상단부의 오늘로 가는 버튼 + <년+월> 버튼 및 알림구현
  let tableHeader = document.createElement('div');
  
  //정의
  let goTodayBtn = document.createElement('button');
  let leftArrow = document.createElement('div');
  let rightArrow = document.createElement('div');
  let showingYearAndMonth = document.createElement('div');

  //구현상세
  goTodayBtn.addEventListener('click',()=>{
    // currentDate = new Date(); setupCalendar()내부에 이미 있는 코드
    setupCalendar();
  })
  leftArrow.classList.add('arrow');
  leftArrow.addEventListener('click',()=>{
    currentDate.setMonth(currentDate.getMonth()-1);
    setupCalendar(currentDate);
    //showingYearAndMonth이쪽의 글자도 갱신해줘야함
    //showingYearAndMonth를 갱신하는 메서드도 필요
  })
  rightArrow.classList.add('arrow');
  rightArrow.addEventListener('click',()=>{
    currentDate.setMonth(currentDate.getMonth()+1);
    setupCalendar(currentDate);
  })

  //부착
  tableHeader.appendChild(goTodayBtn);
  tableHeader.appendChild(leftArrow);
  tableHeader.appendChild(showingYearAndMonth);
  tableHeader.appendChild(rightArrow);

  modal_body.appendChild(tableHeader);



  // 주어진 조건의 달이 첫번째 요일이 무슨요일로 시작하는지 계산
  // 주어진 조건의 1달전의 몇개의 요일이 필요한지 계산
  firstDayOfMonth = getFirstDayOfMonth(currentDate);
  let previousMonthDays = firstDayOfMonth;
  let needPreviousMonthDays = true;

  //테이블의 제일 좌측 상단의 시간 (년월일포함);
  //테이블 이 기준이 되는값
  const mainDate = getDateNDaysAgo(currentDate, previousMonthDays);




  // 달력 요소 생성
  var calendarTable = document.createElement("table");
  var calendarHead = document.createElement("thead");
  var calendarBody = document.createElement("tbody");

  let count = 0;
  let iterateDate;
  //thead 세팅 구현중
  var row = calendarHead.insertRow();
  row.classList.add('row');
  for (var j = 0; j < 7; j++) {
    var cell = row.insertCell();
    cell.classList.add('col');

    cell.innerHTML = ''+getTodayDay(j);
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

      var cellElement = document.createElement("div");

      if (mainDate < realToday) {
        //현실기준 과거시점은 예약불가
        cellElement.innerHTML = iterateDate.getDate();
        cellElement.classList.add("unavailableDate");
      } else if (mainDate >= realToday) {
        //현실보다 미래시점 예약가능 //오늘이지만 과거시점의 예외처리 필요
        cellElement.innerHTML =
          "<a href=" +
          setupCalendar(iterateDate) +
          ">" +
          iterateDate.getDate() +
          "</a>";
        //이쪽에 저 a link가 눌렸을때 아래 예약가능시간 탭도 갱신해주는 코드 추가필요;
        cellElement.classList.add("availableDate");
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
  // reservationDateTable.appendChild(calendarTable);
  setupReservableTimes(currentDate);
};

//setupCalendar();

function getTodayDay(dayIndex) {
  const days = ["일", "월", "화", "수", "목", "금", "토"];
  if (typeof(dayIndex) != 'number') {
    return;
  }
  return days[dayIndex % 7];
}

function getFirstDayOfMonth(firstDayOfMonth) {
  firstDayOfMonth.setDate(1);
  return firstDayOfMonth.getDay();
}

function getDateNDaysAgo(standardDate, n) {
  var pastDate = new Date(standardDate.getTime() - n * 24 * 60 * 60 * 1000);
  return pastDate;
}

function getDateNDaysNext(standardDate, n) {
  var nextDate = new Date(standardDate.getTime() + n * 24 * 60 * 60 * 1000);
  return nextDate;
}
