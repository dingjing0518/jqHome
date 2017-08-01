$(function () {
    var months = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        margin = {left: 30, right: 60, bottom: 30, top: 20},
        width = $('div.container').width()-50 - margin.left - margin.right,
        height = 450 - margin.bottom - margin.top,
        xScale = d3.scale.linear().range([0, width]),
        yScale = d3.scale.linear().range([height, 0]),
        url = global.context + '/admin/tradingReport/data';

	var svg = d3.select('div.container').append('svg')
		.attr('width', width + margin.left + margin.right)
		.attr('height', height + margin.bottom + margin.top).append('g')
		.attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    var xAxis = d3.svg.axis().scale(xScale).orient('bottom');
    var yAxis = d3.svg.axis().scale(yScale).orient('left');

    var r = 10;

    d3.json(url, function (error, data) {
        if (error)
            console.log(error);
        draw(data);
    });

    d3.select('#year').on('change', function () {
        var year = d3.select(this)[0][0].value;

        d3.json(url + '?year=' + year, function (err, data) {
            if (err)
                throw err;
            reDraw(data);
        });
    });

	function mouseover(data){
		svg.append('line')
			.attr('class','dash')
			.attr('x1',0).attr('y1',yScale(data.amount))
			.attr('x2',xScale(data.number)).attr('y2',yScale(data.amount))
			.attr('stroke','black').attr('stroke-dasharray',4);
		svg.append('line')
			.attr('class','dash')
			.attr('x1',xScale(data.number)).attr('y1',yScale(data.amount))
			.attr('x2',xScale(data.number)).attr('y2',height)
			.attr('stroke','black').attr('stroke-dasharray',4);
		svg.append('text')
			.attr('class','label')
			.text('笔数：'+data.number+',金额：'+data.amount)
			.attr('transform',function(){
				var x=xScale(data.number)-3*r;
				var y=yScale(data.amount)-r-r/2;
                return 'translate(' + x + ',' + y + ')';
			})
	}
	
	function mouseout(data){
		svg.selectAll('line.dash').remove();
		svg.selectAll('text.label').remove();
	}

    function reDraw(data) {
        data.forEach(function (d) {
            d.numbers = +d.number;
            d.amounts = +d.amount;
        });

        yScale.domain([0, d3.max(data, function (d) {
            return d.amounts;
        })]).nice();
        xScale.domain([0, d3.max(data, function (d) {
            return d.numbers;
        })]).nice();

        yAxis = d3.svg.axis().scale(yScale).orient('left');
        xAxis = d3.svg.axis().scale(xScale).orient('bottom');

        d3.select('#xAxis').call(xAxis);
        d3.select('#yAxis').call(yAxis);

        var circle =svg.selectAll('g.circle-point')
            .data(data, function (d) {
                return d.time;
            });

        circle.enter()
            .append('g')
            .attr('class', 'circle-point')
            .attr("transform", function (d) {
                return 'translate(' + xScale(d.numbers) + ',' + yScale(d.amounts) + ')';
            });

        circle.exit().remove();

        d3.selectAll('g.circle-point')
            .append('circle')
            .style("fill", "pink")
            .style("stroke", "black")
            .style("stroke-width", "1px")
            .attr('r', r)
            .on('mouseover',mouseover)
            .on('mouseout',mouseout);

        d3.selectAll('g.circle-point')
            .append('text')
            .style("text-anchor", "middle")
            .attr('y', 2 * r)
            .style("font-size", "10px")
            .text(function (d) {
                return d.month;
            });
    }

    function draw(data) {
        data.forEach(function (d) {
            d.numbers = +d.number;
            d.amounts = +d.amount;
        });

        yScale.domain([0, d3.max(data, function (d) {
            return d.amounts;
        })]).nice();
        xScale.domain([0, d3.max(data, function (d) {
            return d.numbers;
        })]).nice();

        svg.selectAll('g')
            .data(data, function (d) {
                return d.time;
            })
            .enter()
            .append('g')
            .attr('class', 'circle-point')
            .attr("transform", function (d) {
                return 'translate(' + xScale(d.numbers) + ',' + yScale(d.amounts) + ')';
            });

        svg.append('g')
            .attr('id', 'xAxis')
            .attr('class', 'x axis')
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis)
            .append('text')
            .text('交易笔数')
            .attr('class','xlabel')
            .attr('x',width+margin.left)
            .attr('y','8')
            .attr("dy", ".71em")
            .style("text-anchor", "end");

        svg.append('g').attr('id', 'yAxis').attr('class', 'y axis').call(yAxis)
            .append('text')
            .text('交易金额')
            .attr('class','ylabel')
            .attr("transform", "rotate(-90)")
            .attr("y", 6)
            .attr("dy", ".71em")
            .style("text-anchor", "end");

        var circle = svg.selectAll('g.circle-point');

        circle
            .append('circle')
            .style("fill", "pink")
            .style("stroke", "black")
            .style("stroke-width", "1px")
            .attr('r', r)
            .on('mouseover',mouseover)
            .on('mouseout',mouseout);

        circle
            .append('text')
            .style("text-anchor", "middle")
            .attr('y', 2 * r)
            .style("font-size", "10px")
            .text(function (d) {
                return d.month;
            });
    }
});