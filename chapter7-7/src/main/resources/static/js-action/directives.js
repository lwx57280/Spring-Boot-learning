/**1、定义一个指令名为datePicker。*/
actionApp.directive('datePicker', function () {
    return {
        restrict: 'AC', /***2、限制为属性指令和和样式指令。*/
        link: function (scope, elem, attrs) {       /***3、使用link方法来定义指令，在link方法内可使用当前scope、当前元素及
         元素属性。*/
            elem.datepicker();  /**4、初始化jquery-ui的datePicker(jquery的写法是$('#id').datePicker())。*/
        }
    };
});