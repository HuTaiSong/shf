var util = {
    getQueryVariable(variable) {  //info.html?id=12&name=zhangsan
        var query = window.location.search.substring(1); //id=12&name=zhangsan
        var vars = query.split("&"); // [id=12,name=zhangsan ]
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");// [id,12] [name,zhangsan]
            if(pair[0] == variable){return pair[1];} //12
        }
        return(false);
    },

    getOriginUrl() {
        var query = window.location.search.substring(1);
        if(query.indexOf("originUrl") != -1) {
            var vars = query.split("originUrl=");
            return vars[1];
        }
        return ""
    }
}