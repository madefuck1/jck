function search_submit(value) {
    var val = $(value).parent().find('input').val();
    window.location.href = '/product/search?searchName=' + val;
}