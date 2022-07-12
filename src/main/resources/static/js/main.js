$(document).ready(function() {
	var auxiliar = 0;
	
	$.ajax({
	  url: "/book",
	  context: document.body,
	  method: "GET"
	}).done(function(data) {
	  	fillBooksTable(data);
	});
	
	$.ajax({
	  url: "/reader",
	  context: document.body,
	  method: "GET"
	}).done(function(data) {
	  	fillReadersTable(data);
	});
	
	console.log("PRIMERO" + auxiliar);
});

function fillBooksTable(books) {
	for (var i=0;i<books.length;i++) {
		var book = books[i];
		var copiesIds = book.copies.filter(e => e.status == 1).map(e => e.id);
		var typeDesc = mapType(book.type);
		
		$("#book_table tbody").append(
			'<tr>'+
			'<td><input type="text" disabled value="'+book.title+'" />' +
			'</td><td><input type="text" disabled value="'+typeDesc+'" />'+
			'</td><td><input type="text" disabled value="'+book.editorial+'" />'+
			'</td><td><input type="text" disabled value="'+book.year+'" />'+
			'</td><td><input type="text" disabled value="'+copiesIds+'" />'+
			'</td><td><input type="button" value="Eliminar" onclick="deleteBook('+book.id+')" /></td>'+
			'</tr>'
		);
	}
}

function fillReadersTable(readers) {
	for (var i=0;i<readers.length;i++) {
		var reader = readers[i];
		var copiesIds = reader.borrows.map(e => e.copy.id);
		$("#reader_table tbody").append(
			'<tr>'+
			'<td><input type="text" value="'+reader.name+'" />' +
			'</td><td><input type="text" value="'+reader.phone+'" />'+
			'</td><td><input type="text" value="'+reader.address+'" />'+
			'</td><td><input type="text" disabled value="'+copiesIds+'" />'+
			'</td><td><input id="borrow'+reader.id+'" type="text" value="" /><input type="button" value="Prestar" onclick="borrowBook('+reader.id+')" /></td>'+
			'</td><td><input id="borrowBack'+reader.id+'" type="text" value="" /><input type="button" value="Devolver" onclick="returnBook('+reader.id+')" /></td>'+
			'</tr>'
		);
	}
}

function createBook() {
	var book = {
		"title": $("#bookTitle").val(),
		"type":  $("#bookType").val(),
		"editorial":  $("#bookEditorial").val(),
		"year":  $("#bookYear").val()
	};
	
	$.ajax({
	  url: "/book?copies="+$("#bookCopies").val(),
	  contentType: "application/json",
	  data: JSON.stringify(book),
	  type: "POST"
	}).done(function() {
		alert("Se ha creado el libro: " + book.title);
		document.location.reload();
    });
}

function deleteBook(id) {
	$.ajax({
	  url: "/book/"+id,
	  contentType: "application/json; charset=utf-8",
	  type: "DELETE"
	}).done(function(data) {
	  	alert("Se ha eliminado el libro: " + id);
		document. location. reload();
	});
}

function borrowBook(readerId) {
	var copy = {
		"id":$("#borrow"+readerId).val()
	}
	
	$.ajax({
	  url: "/reader/"+readerId+"/borrow",
	  contentType: "application/json",
	  data: JSON.stringify(copy),
	  type: "POST",
      error: function (xhr, ajaxOptions, thrownError) {
        alert(xhr.responseText);
      }
	}).done(function(data) {
	  	alert("Se ha prestado la copia: " + copy.id);
		document.location.reload();
	});
}

function returnBook(readerId) {
	var copy = {
		"id":$("#borrowBack"+readerId).val()
	}
	
	$.ajax({
	  url: "/reader/"+readerId+"/borrow/back",
	  contentType: "application/json",
	  data: JSON.stringify(copy),
	  type: "POST"
	}).done(function(data) {
	  	alert("Se ha devuelto la copia: " + copy.id);
		document.location.reload();
	});
}

function mapType(typeId){
	switch(typeId){
		case 1:
		 return "Novela";
		case 2:
		 return "Teatro";
		case 3:
		 return "Poesia";
		case 4:
		 return "Ensayo";
	
	}
}