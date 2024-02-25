let reservationDateTableUp_btn = document.getElementById(
  "reservationDateTableUp_btn"
);
let reservationDateTable = document.getElementById("reservationDateTable");
let currentDate = new Date();
const realToday = new Date(); //절대 바뀌지 않는 오늘의 값
let storeNum;

let previousDisplayStyle;

function fetchList() {
  //검색으로 가져온 목록들
  let search_option = document.getElementById("search_option").value;
  let search_input = document.getElementById("search_input").value;
  let fetch_path = "/api/" + search_option + "/" + search_input;
  //console.log(fetch_path);

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

  fetch(fetch_path)
    .then((result) => {
      let data = result.json();
      if (data != null) {
        searched_list.innerHTML = "";
      }
      return data;
    })
    .then((data) => {
      // console.log(data);
      data.forEach((store) => {
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
        let store_name_div = document.createElement("div");
        let store_path_div = document.createElement("div");

        images_div.classList.add("images_div");
        store_name_div.classList.add("store_name_div");
        store_path_div.classList.add("store_path_div");

        let images_img = document.createElement("img");
        images_img.setAttribute("src", images[0]);
        images_img.setAttribute("alt", "이미지 경로 유실");
        images_div.appendChild(images_img);

        store_name_div.innerText = store.storeName;
        store_path_div.innerText = store.storeLocation;

        let storeNum_div = document.createElement("input");
        storeNum_div.setAttribute("type", "hidden");
        storeNum_div.setAttribute("value", store.storeNum);

        store_div.appendChild(images_div);
        store_div.appendChild(store_name_div);
        store_div.appendChild(store_path_div);
        store_div.appendChild(storeNum_div);
        store_div.addEventListener("click", showReservableTable);

        searched_list.appendChild(store_div);
      });
    })
    .catch((error) => {
      console.log(error);
      console.log("조회된 데이터가 없습니다.");
      let nothing_searched_div = document.createElement("div");

      nothing_searched_div.classList.add("nothing_searched_div");
      nothing_searched_div.innerHTML = "조회된 데이터가 없습니다.";

      document
        .getElementById("searched_list")
        .appendChild(nothing_searched_div);
    });
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

const showReservableTable = (e) => {
  reservationDateTable.style.display = "block";
  let main = e.currentTarget;
  console.log(main);
  if (
    e.currentTarget.querySelector('input[type="hidden"]') != null &&
    storeNum != e.currentTarget.querySelector('input[type="hidden"]').value
  ) {
    storeNum = e.currentTarget.querySelector('input[type="hidden"]').value;
    currentDate = new Date();
    console.log("showReservableTable 중 정상적인 갱신");
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

const setupReservableTimes = (currentDate) => {
  const reservableTimes_div = document.createElement("div");
  reservableTimes_div.classList.add("reservableTimes_div");
  var currentDateJson = JSON.stringify({
    year: currentDate.getFullYear(),
    month: currentDate.getMonth(),
    day: currentDate.getDate(),
  });
  fetch("/api/v1/store/" + storeNum + "/" + currentDateJson)
    .then((data) => {
      return data.json();
    })
    .then((datas) => {
      datas.forEach((data) => {
        console.log(data);
      });
    });
};

const setupCalendar = (inputDate) => {
  reservationDateTable.innerHTML = "";
  //원하는 시간으로 테이블 변경하기 위한 기본 조건
  currentDate = new Date(); //예약하기 위한 타겟이 되는 날짜
  if (inputDate !== undefined) {
    currentDate = inputDate;
  }

  // 주어진 조건의 달이 첫번째 요일이 무슨요일로 시작하는지 계산
  // 주어진 조건의 1달전의 몇개의 요일이 필요한지 계산
  firstDayOfMonth = getFirstDayOfMonth(currentDate);
  let previousMonthDays = firstDayOfMonth;
  let needPreviousMonthDays = true;

  //테이블의 제일 좌측 상단의 시간 (년월일포함);
  //테이블 이 기준이 되는값
  const mainDate = getDateNDaysAgo(currentDate, previousMonthDays);
  // let isDateFasterThanRealtime = false;
  // function revalidateFasterThanRealtime (){
  //   if(mainDate>realToday){
  //     isDateFasterThanRealtime = true;
  //   }
  //   return isDateFasterThanRealtime;
  // }
  // revalidateFasterThanRealtime();

  // 달력 요소 생성
  var calendarTable = document.createElement("table");
  var calendarHead = document.createElement("thead");
  var calendarBody = document.createElement("tbody");

  let count = 0;
  let iterateDate;
  for (var i = 0; i < 6; i++) {
    //행이 6개
    var row = calendarBody.insertRow();
    for (var j = 0; j < 7; j++) {
      //열이 7개 일월화수목금토
      iterateDate = getDateNDaysNext(mainDate, count);
      var cell = row.insertCell();

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
  calendarTable.appendChild(calendarBody);
  reservationDateTable.appendChild(calendarTable);
  setupReservableTimes(currentDate);
};

//setupCalendar();

function getTodayDay(dayIndex) {
  const days = ["일", "월", "화", "수", "목", "금", "토"];
  if (dayIndex != Number) {
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
