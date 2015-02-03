% Specify ellipsoid parameters
center = [0.1,0.1,0.1];
a = 2;
b = 5;
c = 7;
pole = [0 0 0 0 1 1];
show_ellipsoid = false;
show_hull = false;
show_lattice = true;
show_grid = false;
show_pole = false;
% Set axis
axis_scale = max([a,b,c]);

hold on
if (show_grid)
    % Generate integer lattice
    X2 = [];
    Y2 = [];
    Z2 = [];
    hold on
    for i2=-axis_scale:axis_scale
        for j2=-axis_scale:axis_scale
            for k2=-axis_scale:axis_scale
                X2(end+1) = i2;
                Y2(end+1) = j2;
                Z2(end+1) = k2;
            end
        end
    end
    hold off
    scatter3(X2,Y2,Z2,'bl','.');
end
hold off

% Generate integer lattice
X = [];
Y = [];
Z = [];
hold on
for i=-a:a
    for j=-b:b
        for k=-c:c
            if ((i-center(1)).^2/a.^2 + (j-center(2)).^2/b.^2 + (k-center(3)).^2/c.^2 <= 1)
                X(end+1) = i;
                Y(end+1) = j;
                Z(end+1) = k;
            end
        end
    end
end
hold off

% Plot ellipsoid
hold on
if (show_ellipsoid)
    [x, y, z] = ellipsoid(center(1),center(2),center(3),a,b,c);
    mesh(x, y, z,'facecolor','none','edgecolor','r');
end
hold off

% Plot convex hull
k = convhull(X,Y,Z,'simplify',true);
hold on
if (show_hull)
    trimesh(k,X,Y,Z,'facecolor','none'); %,'edgecolor','y');
end
hold off

% Assign shelling order to each triangle
hold on
for i=1:(size(k))
    v1 = [X(k(i,1)), Y(k(i,1)), Z(k(i,1))];
    v2 = [X(k(i,2)), Y(k(i,2)), Z(k(i,2))];
    v3 = [X(k(i,3)), Y(k(i,3)), Z(k(i,3))];
    t = intersectLinePlane(pole,[v1 v2-v1 v3-v1]);
    if (t(3) < 0) 
        t(3) = 10000000+t(3);
    end
    if (isnan(t(3))) 
        t(3) = 100000;
    end
    k(i,4) = t(3);
end
hold off

k = sortrows(k, 4);



hold on
% Plot integer lattice
if (show_lattice)
    scatter3(X,Y,Z,'bl','.');
end

% Plot pole
if (show_pole)
    quiver3(pole(1), pole(2), pole(3), pole(4)*axis_scale, pole(5)*axis_scale, pole(6)*axis_scale);
end
hold off


axis([-axis_scale axis_scale -axis_scale axis_scale -axis_scale axis_scale])
camproj('orthographic')

hold on
for i=1:(size(k))
    Xcoords = [X(k(i,1)), X(k(i,2)), X(k(i,3))];
    Ycoords = [Y(k(i,1)), Y(k(i,2)), Y(k(i,3))];
    Zcoords = [Z(k(i,1)), Z(k(i,2)), Z(k(i,3))];
    fill3(Xcoords, Ycoords, Zcoords, 1)
    if(i < size(k,1))
        Xcoords2 = [X(k(i+1,1)), X(k(i+1,2)), X(k(i+1,3))];
        Ycoords2 = [Y(k(i+1,1)), Y(k(i+1,2)), Y(k(i+1,3))];
        Zcoords2 = [Z(k(i+1,1)), Z(k(i+1,2)), Z(k(i+1,3))];
        if (~isCoplanar([Xcoords Xcoords2]', [Ycoords Ycoords2]', [Zcoords Zcoords2]'))  
            edge1 = [X(k(i,1))-X(k(i,2)),Y(k(i,1))-Y(k(i,2)),Z(k(i,1))-Z(k(i,2))];
            view(edge1)
            temp1 = plot3([X(k(i,1)), X(k(i,2))],[Y(k(i,1)), Y(k(i,2))],[Z(k(i,1)), Z(k(i,2))],'LineWidth',4);
            pause(2)
            set(temp1,'LineWidth',1);
            
            edge2 = [X(k(i,1))-X(k(i,3)),Y(k(i,1))-Y(k(i,3)),Z(k(i,1))-Z(k(i,3))];
            view(edge2)
            temp2 = plot3([X(k(i,1)), X(k(i,3))],[Y(k(i,1)), Y(k(i,3))],[Z(k(i,1)), Z(k(i,3))],'LineWidth',4);
            pause(2)
            set(temp2,'LineWidth',1);
            
            edge3 = [X(k(i,2))-X(k(i,3)),Y(k(i,2))-Y(k(i,3)),Z(k(i,2))-Z(k(i,3))];
            view(edge3)
            temp3 = plot3([X(k(i,2)), X(k(i,3))],[Y(k(i,2)), Y(k(i,3))],[Z(k(i,2)), Z(k(i,3))],'LineWidth',4);
            pause(2)
            set(temp3,'LineWidth',1);
        end
    end
end
hold off