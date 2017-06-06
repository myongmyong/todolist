(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
	$(document).ready(function(){
        
        getTodoList();
        
        insertTodo();
        
        updateCompleted();

        deleteTodo();

        selectByCompleted();

        clearCompleted();       
	
    });

    function getTodoList(){
        $.ajax({
            url : "./api/todos/getTodos",
            type : "get",
            dataType : "json",
            success : function(response){
                for(var i=0;i<response.length;i++){
                    var obj = response[i];
                        if(obj.completed == 0){
                            $('.todo-list').prepend('<li id='+obj.id+'>'+'<div class="view">'+'<input class="toggle" type="checkbox">'+'<label>'+obj.todo+'</label>'+'<button class="destroy">'+'</button>'+'</div>'+'</li>');
                        
                        }else if(obj.completed == 1){
                            $('.todo-list').prepend('<li id='+obj.id+' class="completed">'+'<div class="view">'+'<input class="toggle" type="checkbox" checked>'+'<label>'+obj.todo+'</label>'+'<button class="destroy">'+'</button>'+'</div>'+'</li>');
                        }
                        countLi();
                    }
                }
        });        
    }

    function insertTodo(){
        $('.new-todo').keyup(function(key){
            if(key.keyCode === 13){
                var content = $('.new-todo').val();
                if(content === ''){
                    alert('할 일을 입력해주세요.');
                }else{
                    $.ajax({
                        url : "./api/todos/insertTodo",
                        type : "post",
                        dataType : "json",
                        contentType: "application/json;charset=UTF-8",
                        data : JSON.stringify({"todo" : content}),
                        success : function(response){
                            var obj = response;
                            $('.todo-list').prepend('<li id='+obj.id+'>'+'<div class="view">'+'<input class="toggle" type="checkbox">'+'<label>'+obj.todo+'</label>'+'<button class="destroy">'+'</button>'+'</div>'+'</li>');
                            $('.new-todo').val('');
                            countLi();
                        },
                        error : function(req,status,error){
                            alert('Error');
                        }
                    });    
                }
            }
        });  
    }

    function updateCompleted(){
        $('.todo-list').on('click','.toggle',function(){
            var parLi = $(this).parents('li');
            var id = parLi.attr('id');
            var completed;
            if(parLi.is('.completed') === true){
                parLi.attr('class','');
                completed = 0;
            }else{
                parLi.attr('class','completed');
                completed = 1;
            }
            $.ajax({
                url : "./api/todos/updateCompleted",
                type : "put",
                dataType : "json",
                contentType: "application/json;charset=UTF-8",
                data : JSON.stringify({"id" : id, "completed" : completed}),
                success : function(response){
                    countLi();
                }
            });
        });     
    }

    function deleteTodo(){
        $('.todo-list').on('click','.destroy',function(){
            var parLi = $(this).parents('li');
            var id = parLi.attr('id');
            $.ajax({
                url : "./api/todos/deleteTodo",
                type : "delete",
                dataType : "json",
                contentType : "application/json;charset=UTF-8",
                data : JSON.stringify({"id" : id}),
                success : function(response){
                    parLi.remove();
                    countLi();
                },
                error : function(req,status,error){
                    console.log('error');
                }
            });
        });
    }

    function countLi(){
        var total = $('ul.todo-list li').length;
        var completedLi = $('ul.todo-list li.completed').length;
        var uncompleted = total - completedLi;
        $('.todo-count strong').text(uncompleted); 
    }

    function selectByCompleted(){
        $('.filters').on('click','a',function(){
            var index = $('ul.filters li a').index(this);
            var todoInput = 'ul.todo-list li div input';
            var checked = $(todoInput+':checked').parents('li');
            var unchecked = $(todoInput).not(todoInput+':checked').parents('li');
            if(index == 0){
                $('ul.filters li a').attr('class','');
                $(this).attr('class','selected');
                $('ul.todo-list li').show();
            }else if(index == 1){
                $('ul.filters li a').attr('class','');
                $(this).attr('class','selected');
                unchecked.show();
                checked.hide();
            }else if(index == 2){
                $('ul.filters li a').attr('class','');
                $(this).attr('class','selected');
                unchecked.hide();
                checked.show();
            }
        });
    }

    function clearCompleted(){
        $('button.clear-completed').click(function(){
            var todoInput = 'ul.todo-list li div input';
            var checked = $(todoInput+':checked').parents('li');
            checked.remove();
            $.ajax({
                url : './api/todos/clearCompleted',
                type : 'delete'
            });
        });
    }

})(window);