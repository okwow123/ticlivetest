let reservationDateTableUp_btn = document.getElementById('reservationDateTableUp_btn');
let reservationDateTable = document.getElementById('reservationDateTable');
let currentDate = new Date();
const realToday = new date(); //절대 바뀌지 않는 오늘의 값

reservationDateTableUp_btn.addEventListener('click', function() {
  console.log("reservationDateTableUp_btn PRESSED");
  console.log(reservationDateTableUp_btn);
  console.log(reservationDateTable);
  var parent = document.getElementById('contactme');
  console.log(parent);

  reservationDateTable.style.display = 'block';
  reservationDateTable.style.left = parent.offsetLeft+'px';
  reservationDateTable.style.width = parent.offsetWidth+'px';
  reservationDateTable.style.backgroundColor = '#f1f1f1';
  // reservationDateTable.style.padding = '20px';
  // reservationDateTable.style.animation = 'slideUp 0.5s ease';
//    setTimeout(function() {
//      div.style.display = 'none';
//    }, 2000); // Adjust the timeout as needed
});

document.getElementById('cancelReserving').addEventListener('click',()=>{
  reservationDateTable.style.display = 'none';
})

const setupCalendar = (year,month,date) => {
  //원하는 시간으로 테이블 변경하기 위한 기본 조건
  currentDate = new Date();
  if(year === undefined){
    year = currentDate.getFullYear();
  }else{
    currentDate.setFullYear(year);
  }

  if(month === undefined){
    month = currentDate.getMonth();
  }else{
    currentDate.setMonth(month);
  }

  if(date === null){
    date = currentDate.setMonth();
  }else{
    currentDate.setMonth();
  }

  // 주어진 조건의 달이 첫번째 요일이 무슨요일로 시작하는지 계산
  // 주어진 조건의 1달전의 몇개의 요일이 필요한지 계산
  firstDayOfMonth = getFirstDayOfMonth(currentDate);
  let previousMonthDays = firstDayOfMonth;
  let needPreviousMonthDays = true; 

  //테이블의 제일 좌측 상단의 시간 (년월일포함);
  //테이블 이 기준이 되는값
  const mainDate = getDateNDaysAgo(currentDate,previousMonthDays); 
  let isDateFasterThanRealtime = false;
  function revalidateFasterThanRealtime (){
    if(mainDate>realToday){
      isDateFasterThanRealtime = true;
    }
    return isDateFasterThanRealtime;
  }
  revalidateFasterThanRealtime();

  // 달력 요소 생성
  var calendarTable = document.createElement('table');
  var calendarBody = document.createElement('tbody');
  
  let count = 0;
  for (var i = 0; i < 6; i++) {
    var row = calendarBody.insertRow();
    for (var j = 0; j < 7; j++) {
      var cell = row.insertCell();

      var cellElement = document.createElement('div');

      if(mainDate>realToday){
        
      }

      
      



      cell.appendChild(cellElement);
    }
  }

  // 생성된 달력 요소를 reservationDateTable에 추가
  calendarTable.appendChild(calendarBody);
  reservationDateTable.appendChild(calendarTable);

};

setupCalendar();

function getTodayDay(dayIndex) {
  var days = ['일', '월', '화', '수', '목', '금', '토'];
  if(dayIndex!=Number)
  {
    return;
  }
  return days[dayIndex%7];
}

function getFirstDayOfMonth(firstDayOfMonth) {
  firstDayOfMonth.setDate(1);
  return firstDayOfMonth.getDay();
}

function getDateNDaysAgo(standardDate,n) {
  var pastDate = new Date(standardDate.getTime() - n * 24 * 60 * 60 * 1000);
  return pastDate.getDate();
}

function getDateNDaysNext(standardDate,n) {
  var nextDate = new Date(standardDate.getTime() + n * 24 * 60 * 60 * 1000);
  return nextDate.getDate();
}