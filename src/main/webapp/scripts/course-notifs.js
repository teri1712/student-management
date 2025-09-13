(function(){
  'use strict';

  function formatRelativeTime(iso){
    try{
      var d = new Date(iso);
      var now = new Date();
      var diff = Math.floor((now - d)/1000);
      if (isNaN(diff)) return iso;
      if (diff < 5) return 'just now';
      if (diff < 60) return diff + ' seconds ago';
      if (diff < 3600) { var m = Math.floor(diff/60); return m + ' minute' + (m>1?'s':'') + ' ago'; }
      if (diff < 86400) { var h = Math.floor(diff/3600); return h + ' hour' + (h>1?'s':'') + ' ago'; }
      var timeStr = d.toLocaleTimeString([], {hour:'2-digit', minute:'2-digit'});
      var yesterday = new Date(now); yesterday.setDate(now.getDate()-1);
      if (d.toDateString() === yesterday.toDateString()) return 'yesterday at ' + timeStr;
      var dateStr = d.toLocaleDateString([], {year:'numeric', month:'short', day:'2-digit'});
      return dateStr + ' ' + timeStr;
    }catch(e){ return iso; }
  }

  function getMeta(){
    var list = document.getElementById('notifList');
    if (!list) return null;
    var ctx = list.dataset.ctx || '';
    var courseId = list.dataset.courseId || '';
    var year = list.dataset.year || '';
    return { list: list, ctx: ctx, courseId: courseId, year: year };
  }

  function renderNotifs(arr, state){
    if (!Array.isArray(arr)) return;
    if (!state.lastId) state.list.innerHTML = '';
    arr.sort(function(a,b){ return a.id - b.id; });
    for (var i=0;i<arr.length;i++){
      var n = arr[i];
      var li = document.createElement('li');
      li.className = 'list-group-item ps-3 notif-item';
      var avatar = (n.teacher && n.teacher.length>0) ? n.teacher.charAt(0).toUpperCase() : '?';
      li.innerHTML = '<div class="d-flex align-items-start gap-3">'
        + '<div class="rounded-circle bg-warning-subtle border border-warning text-dark fw-bold d-inline-flex align-items-center justify-content-center" style="width:40px;height:40px;">' + avatar + '</div>'
        + '<div class="flex-grow-1">'
        +   '<div class="d-flex justify-content-between">'
        +     '<div class="fw-semibold">' + (n.teacher || '') + '</div>'
        +     '<div class="text-muted small">' + formatRelativeTime(n.createdAt) + '</div>'
        +   '</div>'
        +   '<div class="mt-1 text-body">' + (n.content || '') + '</div>'
        + '</div>'
        + '</div>';
      state.list.appendChild(li);
      state.lastId = n.id;
    }
  }

  function fetchNotifs(state){
    var url = state.ctx + '/notifications?courseId=' + encodeURIComponent(state.courseId) + '&year=' + encodeURIComponent(state.year);
    if (state.lastId) url += '&sinceId=' + state.lastId;
    fetch(url)
      .then(function(r){ return r.json(); })
      .then(function(arr){ renderNotifs(arr, state); })
      .catch(function(){});
  }

  function setupPosting(meta, state){
    var btn = document.getElementById('notifSend');
    var input = document.getElementById('notifContent');
    if (!btn || !input) return; // student page, no posting

    function postNotif(){
      var content = input.value.trim();
      if (!content) return;
      var formData = new URLSearchParams();
      formData.set('courseId', meta.courseId);
      formData.set('year', meta.year);
      formData.set('content', content);
      fetch(meta.ctx + '/notifications', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData.toString()
      }).then(function(r){
        if (r.ok){ input.value=''; fetchNotifs(state); }
      }).catch(function(){});
    }

    btn.addEventListener('click', postNotif);
  }

  document.addEventListener('DOMContentLoaded', function(){
    var meta = getMeta();
    if (!meta) return;
    var state = { lastId: null, list: meta.list, ctx: meta.ctx, courseId: meta.courseId, year: meta.year };
    fetchNotifs(state);
    setInterval(function(){ fetchNotifs(state); }, 10000);
    setupPosting(meta, state);
  });
})();
